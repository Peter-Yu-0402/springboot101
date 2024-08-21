package org.jaesung.springbootdeveloper.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller // 컨트롤러로 명시했기 때문에 스프링부트에서 반환하는 값의 이름(이 경우, example)을 가진 뷰의 파일을 찾게 된다.
public class ExampleController {
    @GetMapping("/thymeleaf/example")
    public String thymeleafExample(Model model) { // 뷰로 데이터를 넘겨주는 모델 객체
        Person examplePerson = new Person();
        examplePerson.setId(1L);
        examplePerson.setName("홍길동");
        examplePerson.setAge(11);
        examplePerson.setHobbies(List.of("운동", "독서"));

        model.addAttribute("person", examplePerson); // Person 객체 저장
        model.addAttribute("today", LocalDate.now());

        return "example"; // example.html라는 뷰를 조회한다.
        // resources/templates/example.html을 찾은 다음 웹브라우저에서 해당 파일을 보여준다.
    }

    @Setter
    @Getter
    class Person {
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }
}
