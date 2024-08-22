package org.jaesung.springbootdeveloper.repository;

import org.jaesung.springbootdeveloper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // email로 사용자 정보를 가져옴
    // 사실상 사용자 이름으로 봐도 된다.
}
