package org.jaesung.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.jaesung.springbootdeveloper.domain.User;
import org.jaesung.springbootdeveloper.dto.AddUserRequest;
import org.jaesung.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 패스워드 인코딩 용으로 등록한 빈이다.

    public Long save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                // 패스워드 암호화
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();
    }
}
