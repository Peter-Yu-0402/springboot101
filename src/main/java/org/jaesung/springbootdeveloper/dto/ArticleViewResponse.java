package org.jaesung.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jaesung.springbootdeveloper.domain.Article;

import java.time.LocalDateTime;

@NoArgsConstructor // 기본 생성자로 일단 객체를 만들고 나중에 필드값을 설정하는 경우가 많기 때문에 꼭 필요하다.
@Getter
public class ArticleViewResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    // 유연함을 위해 ArticleListViewResponse 클래스와는 달리 final 키워드를 사용하지 않았다.

    public ArticleViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
    }
}
