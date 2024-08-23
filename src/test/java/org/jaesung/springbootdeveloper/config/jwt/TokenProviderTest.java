package org.jaesung.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Jwts;
import org.jaesung.springbootdeveloper.domain.User;
import org.jaesung.springbootdeveloper.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    // generateToken() 검증 테스트
    @DisplayName("generateToken() : 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {
        // given
        // 토큰에 유저 정보를 추가하기 위한 테스트 유저 만들기
        User testUser = userRepository.save(User.builder()
                .email("user@email.com")
                .password("test")
                .build());

        // when
        // ToKenProvider의 generateToken() 메서드를 호출해 토큰을 만들기
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        // Then
        // jjwt 라이브러리로 토큰을 복호화, 토큰을 만들 때 클레임으로 넣어둔 id값이 given절에서 만든 유저 ID와 동일한지 확인한다.
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    // validToken() 검증 테스트
    @DisplayName("validToken(): 만료된 토큰인 경우에 유효성 검증에 실패한다.")
    @Test
    void validToken_invalidToken() {
        // given
        // jjwt 라이브러리를 사용해 토큰 생성 -> 이미 만료된 토큰으로 생성
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isFalse();
    }


    @DisplayName("validToken(): 유효한 토큰인 경우에 유효성 검증에 성공한다.")
    @Test
    void validToken_validToken() {
        // given
        // JwtFactory에서 이미 설정한 만료시간 값(14일 뒤)으로 만료되지 않은 토큰 생성
        String token = JwtFactory.withDefaultValues()
                .createToken(jwtProperties);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isTrue();
    }


    @DisplayName("getAuthentication(): 토큰 기반으로 인증정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        // given
        // createToken()에 주목하라. 토큰이 생성되었다. 빌더 패턴을 이용해 subject만 별도로 설정하고 나머진 default값인 토큰이다.
        String userEmail = "user@email.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);

        // when
        // getAuthentication() 메서드를 호출해 인증 객체 (authentication)을 반환 받는다.
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then
        // 반환된 인증 객체 속 유저 이름과 given에서 설정한 subject가 동일한지 검사
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);
    }

    // 토큰을 프로퍼티즈 파일에 저장된 비밀값으로 복호화decoding한 뒤
    // -> 클레임을 가져오는 private 메서드 getClaims()를 호출
    // -> 클레임 정보 반환 받아 클레임에서 id키로 저장된 값을 가져와 반환한다.
    @DisplayName("getUserId(): 토큰으로 유저 ID를 가져올 수 있다.")
    @Test
    void getUserId() {
        // given
        // 토큰을 생성하는데 클레임도 추가한다. 키는 "id", 값은 1
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);

        // when
        Long userIdByToken = tokenProvider.getUserId(token);

        // then
        assertThat(userIdByToken).isEqualTo(userId);
    }
}