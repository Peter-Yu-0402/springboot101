package org.jaesung.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jaesung.springbootdeveloper.domain.Article;
import org.jaesung.springbootdeveloper.domain.User;
import org.jaesung.springbootdeveloper.dto.AddArticleRequest;
import org.jaesung.springbootdeveloper.dto.UpdateArticleRequest;
import org.jaesung.springbootdeveloper.repository.BlogRepository;
import org.jaesung.springbootdeveloper.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역직렬화를 위한 클래스

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }

    @BeforeEach
    void setSecurityContext() {
        userRepository.deleteAll();
        user = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }


    // Given : 블로그 글 추가에 필요한 요청 객체를 만든다.
    // When : 블로그 글 추가 API에 요청을 보낸다. 이때 요청 타입은 JSON이며, given 절에서 미리 만들어둔 객체를 요청 본문으로 함께 보낸다.
    // Then : 응답 코드가 201 Created인지 확인한다. Blog를 전체 조회해 크기가 1인지 확인하고, 실제로 저장된 데이터와 요청 값을 비교한다.

    @DisplayName("addArticle : 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        // 객체 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("username");

        // when
        // 설정한 내용을 바탕으로 요청 전송
        // mockMvc를 사용해 실제 서버에 요청을 보내고,
        ResultActions result = mockMvc.perform(post(url) // url로 POST 방식으로 요청 보내고
                .contentType(MediaType.APPLICATION_JSON_VALUE) // contentType을 JSON 형식으로 지정 (input)
                .principal(principal)
                .content(requestBody)); // requestBody에서 직렬화된 데이터를 입력 (input)
        // result는 output


        // then
        result.andExpect(status().isCreated()); // output에 대해 아래와 같이 예상한다.

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1); // 크기가 1인지 검증
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    // given : 블로그 글을 저장한다.
    // when : 목록 조회 API를 호출한다.
    // then : 응답 코드가 200OK이고, 반환받은 값 중에 0번째 요소의 content와 title이 저장된 값과 같은지 확인한다.

    @DisplayName("findAllArticles : 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllArticles() throws Exception {
        // given
        final String url = "/api/articles";
        Article savedArticle = createDefaultArticle();

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(savedArticle.getContent()))
                .andExpect(jsonPath("$[0].title").value(savedArticle.getTitle()));
    }

    // given : 블로그 글을 저장한다.
    // when : 저장한 블로그 글의 id값으로 블로그 글 조회 API를 호출한다.
    // then : 응답 코드가 200 OK이고 반환 받은 content와 title이 저장된 값과 같은지 확인한다.

    @DisplayName("findArticle : 블로그 글 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();


        // when
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(savedArticle.getContent()))
                .andExpect(jsonPath("$.title").value(savedArticle.getTitle()));
    }

    // given : 블로그 글을 저장한다.
    // when : 저장한 블로그 글의 id값으로 삭제 API를 호출한다.
    // then : 응답 코드가 200 OK이며, 블로그 글 리스트를 전체 조회해 조회한 배열 크기가 0인지 확인한다.

    @DisplayName("deleteArticle : 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        Article savedArticle = createDefaultArticle();

        // when
        mockMvc.perform(delete(url, savedArticle.getId()))
                .andExpect(status().isOk());

        // then
        List<Article> articles = blogRepository.findAll();

        assertThat(articles).isEmpty();
    }

        @DisplayName("updateArticle : 블로그 글 수정에 성공한다.")
        @Test
        public void updateArticle() throws Exception {
            // given
            final String url = "/api/articles/{id}";
            Article savedArticle = createDefaultArticle();

            final String newTitle = "new title";
            final String newContent = "new content";

            UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

            // when
            // 테스트하고자 하는 HTTP 요청을 컨트롤러에 전달 후 그 결과를 ResultActions 객체로 반환
            ResultActions result = mockMvc.perform(put(url, savedArticle.getId()) // mockMvc를 이용해 실제 HTTP 요청 실행
                    .contentType(MediaType.APPLICATION_JSON_VALUE) // 요청의 Content-Type 헤더 설정 (application/json)
                    .content(objectMapper.writeValueAsString(request))); // request 객체를 JSON 문자열로 변환하고, 요청 본문(content)에 추가
            // BlogApiController의 updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request)를 사용하는 것이다.
            // url, savedArticle.getId()를 통해 long id를 입력하고
            // .content(objectMapper.writeValueAsString(request))를 통해 @RequestBody UpdateArticleRequest request를 입력하는 것이다.


            // then
            result.andExpect(status().isOk());

            Article article = blogRepository.findById(savedArticle.getId()).get();
            // .get() : Optional 객체에서 실제로 존재하는 값을 꺼내기 위해 추가
            // 예외 처리가 안 된 상태이다.
            // NoSuchElementException 예외 발생할 수 있으나 EntityNotFoundException 등 커스텀 예외로 대체 필요하다.
            // orElseThrow 또는 isPresent 조건문 사용이 권고된다.

            assertThat(article.getTitle()).isEqualTo(newTitle);
            assertThat(article.getContent()).isEqualTo(newContent);
        }

    private Article createDefaultArticle() {
        return blogRepository.save(Article.builder()
                .title("title")
                .author(user.getUsername())
                .content("content")
                .build());
    }
}