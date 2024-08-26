//package org.jaesung.springbootdeveloper.config;
//
//import lombok.RequiredArgsConstructor;
//import org.jaesung.springbootdeveloper.service.UserDetailService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//
//@RequiredArgsConstructor
//@Configuration
//public class WebSecurityConfig {
//    private final UserDetailService userService;
//
//    // 특정 경로 내 스프링 시큐리티 기능 비활성화
//    // web.ignoring()을 통해 특정 요청 경로들을 인증/인가 검사에서 제외
//    // 익숙한 permitAll()은 특정 HTTP 요청에 대한 url에 적용하는 것이다.
//    @Bean
//    public WebSecurityCustomizer configure() {
//        return (web) -> web.ignoring()
//                .requestMatchers(toH2Console()) // 개발 환경에서 개발자 편의를 위해 h2 콘솔에 쉽게 접근할 수 있도록 인증인가 열외
//                .requestMatchers("/static/**"); // 일반적으로 정적 리소스(CSS, JavaScript, images)는 인증인가 필요 없음
//    }
//
//    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeRequests() // 인증, 인가 설정
//                // 특정 HTTP 요청과 일치하는 url에 대한 설정이다. 상단의 configure()와는 달리 HTTP 요청에 한정된다.
//                .requestMatchers("/login", "/signup", "/user").permitAll()
//                .anyRequest().authenticated() // 위의 url를 제외한 나머지에 대해서는 인증 authentication
//                .and()
//                .formLogin() // 폼 기반 로그인 설정
//                .loginPage("/login") // 로그인 페이지 경로 설정
//                .defaultSuccessUrl("/articles") // 로그인이 완료되었을 때 이동할 경로 설정
//                .and()
//                .logout() // 로그아웃 설정
//                .logoutSuccessUrl("/login")
//                .invalidateHttpSession(true) // 로그아웃 이후 세션을 전체 삭제할지 여부를 설정
//                .and()
//                .csrf().disable() // csrf 비활성화 : 보안을 위해 활성화 해야 하지만 실습의 편의를 위해 임시 해제
//                .build();
//    }
//
//    // 인증 관리자 관련 설정
//    // 인증 방법(ex. LDAP, JDBC 기반) 설정
//    public AuthenticationManager authenticationManager(HttpSecurity http,
//                                                       BCryptPasswordEncoder bCryptPasswordEncoder,
//                                                       UserDetailService userDetailService) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                // 파라미터에 UserDetailsService를 상속받은 클래스 UserDetailService가 있다.
//                .userDetailsService(userService) // 사용자 정보를 가져올 서비스 설정. 반드시 UserDetailsService를 상속받은 클래스여야 한다.
//                .passwordEncoder(bCryptPasswordEncoder)
//                .and()
//                .build();
//    }
//
//    // 패스워드 인코더로 사용할 빈 등록
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
