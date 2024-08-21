package org.jaesung.springbootdeveloper.dto;

import lombok.Getter;
import org.jaesung.springbootdeveloper.domain.Article;

@Getter
public class ArticleListViewResponse {
    private final Long id;
    private final String title;
    private final String content;

    // 하기 코드가 없다면 변수가 초기화되지 않았다는 에러 메세지가 떴다.
    // 다른 일반적인 코드에는 final로 선언되었지 않았고 이 경우에는 생성자에서 초기화되지 않아도 된다.
    // final로 선언된 변수는 한 번만 초기화될 수 있다.
    // 초기화된 후에는 값을 변경할 수 없다.
    // 반드시 생성자 또는 필드 선언 시 초기화 되어야 한다.
    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
