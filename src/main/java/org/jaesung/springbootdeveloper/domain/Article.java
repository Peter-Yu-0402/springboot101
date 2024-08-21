package org.jaesung.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // 엔티티로 지정
@Getter // 이와 같이 롬복을 활용하면 귀찮게 코드를 일일이 치지 않아도 된다
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id // 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder // 빌더 패턴으로 객체 생성
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 기본 생성자
    // @NoArgsConstructor로 대체할 수 있다.
//    protected Article() {
//
//    }

    // 게터
    // @Getter로 대체할 수 있다.
//    public Long getId() {
//        return id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getContent() {
//        return content;
//    }
}
