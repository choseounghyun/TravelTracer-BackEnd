package com.project.travelTracer.user.repository;

import com.project.travelTracer.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //이메일로 회원 정보 조회
    Optional<User> findByUserLoginID(String userLoginID);

}
