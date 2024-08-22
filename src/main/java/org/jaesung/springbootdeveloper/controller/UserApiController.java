package org.jaesung.springbootdeveloper.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jaesung.springbootdeveloper.dto.AddUserRequest;
import org.jaesung.springbootdeveloper.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService; // @RequiredArgsConstructor를 추가하니까 빨간 줄이 사라졌다!
    // 초기화를 위해 필요했다! 의존성 주입하는 방법이었다!

    @PostMapping("/user")
    public String signup(AddUserRequest request) { // @RequestBody가 없다!
        userService.save(request); // 회원가입 메서드를 호출한다.
        return "redirect:/login"; // 회원가입이 완료된 이후에는 로그인 페이지로 이동한다.
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler() // 로그아웃을 담당하는 핸들러의 logout() 메서드를 호출하여 로그아웃한다.
                .logout(request, response,
                        SecurityContextHolder.getContext().getAuthentication());
                        // SecurityContextHolder 안에 SecurityContext가 있다.
                        // SecurityContextHolder는 인증이 완료된 Authentication을 저장한다.
        return "redirect:/login";
    }
}
