package org.jaesung.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.jaesung.springbootdeveloper.domain.Article;
import org.jaesung.springbootdeveloper.dto.ArticleListViewResponse;
import org.jaesung.springbootdeveloper.dto.ArticleViewResponse;
import org.jaesung.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller // 컨트롤러로 명시했기 때문에 스프링부트에서 반환하는 값의 이름(이 경우, articleList)을 가진 뷰의 파일을 찾게 된다.
// @RestController와는 다르다! @Controller는 반환값으로 뷰를 찾아서 보여준다!
public class BlogViewController {
    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) { // 뷰를 향해 데이터를 넘겨주는 모델 객체
        // ArticleListViewResponse DTO의 리스트 형태 객체
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();

        model.addAttribute("articles", articles); // 블로그 글 리스트를 model에 저장

        return "articleList"; // articleList.html라는 뷰 조회
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        // Article 객체 불러오고 나서 model에 ArticleViewResponse DTO로 데이터 이동시키기
        Article article = blogService.findById(id);
        // 서비스의 findById 메서드에서 예외를 던지는 코드가 이미 작성되었다.
        // 그렇기 때문에, 컨트롤러에서는 해당 예외를 핸들링하는 코드를 작성할 필요없이 바로 호출할 수 있었다.

        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    // 등록/수정 화면을 보여주기 위한 컨트롤러 메서드 추가
    // 단순히 화면을 보여주기 위한 컨트롤러 메서드일 뿐이다.
    // 실제 생성/수정하는 API 기능은 static/js/article.js 파일에 작성되었다. // 버튼을 동작시키기 위해서 자바스크립트로 작성했다!
    @GetMapping("/new-article")
    // id 키를 가진 쿼리 파라미터의 값을 id 변수에 매핑 (id는 없을 수 있음)
    // 왜 PathVariable이 아닌가? id가 없는 경우도 포함시키기 위해서이다!
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) { // id가 없을 경우 생성한다.
            model.addAttribute("article", new ArticleViewResponse()); // 기본생성자로 객체 생성
        } else { // id가 있을 경우 수정한다.
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}
