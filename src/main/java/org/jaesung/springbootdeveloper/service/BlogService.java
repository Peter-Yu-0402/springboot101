package org.jaesung.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jaesung.springbootdeveloper.domain.Article;
import org.jaesung.springbootdeveloper.dto.AddArticleRequest;
import org.jaesung.springbootdeveloper.dto.UpdateArticleRequest;
import org.jaesung.springbootdeveloper.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 서블릿 컨테이너에 등록
public class BlogService {
    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request, String userName) {
        return blogRepository.save(request.toEntity(userName));
    }

    public List<Article> findAll() {
        return blogRepository.findAll();

    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found : " + id));
    }

    public void delete(long id) {
        Article article = blogRepository.findById(id)
                        .orElseThrow(()-> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article); // 게시글 작성자 여부 검증 메서드 호출
        blogRepository.delete(article);
    }

    // 매칭한 메서드를 하나의 트랜잭션으로 묶는 역할
    // 데이터를 바꾸기 위해 묶어진 작업 단위
    // 중간에 실패한다면 트랜잭션의 처음 상태로 다시 되돌아간다.
    // 엔티티의 필드 값이 바뀌면 중간에 에러가 발생해도 제대로된 값 수정을 보장할 수 있다.
    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article); // 게시글 작성자 여부 검증 메서드 호출
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    // 게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("Not authorized");
        }
    }
}
