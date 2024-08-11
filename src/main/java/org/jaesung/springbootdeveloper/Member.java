package org.jaesung.springbootdeveloper;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 자동 생성
@AllArgsConstructor
@Getter
@Entity // 엔티티로 지정하는 역할 : 해당 객체를 JPA가 관리하는 엔티티로 지정함, 다시 말해 클래스와 데이터베이스 테이블을 매핑
public class Member {
    @Id // id 필드를 기본키로 지정하는 역할, 이름을 명시하지 않았기 때문에 자동으로 데이터베이스 member 테이블과 매핑
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id; // DB 테이블의 id 컬럼과 매칭

    @Column(name = "name", nullable = false) // name이라는 not null DB 테이블 컬럼과 매핑
    private String name; // DB 테이블의 name 컬럼과 매칭
}
