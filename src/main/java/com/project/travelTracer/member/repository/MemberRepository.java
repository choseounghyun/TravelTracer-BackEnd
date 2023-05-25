package com.project.travelTracer.member.repository;

import com.project.travelTracer.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);

    boolean existsByUserId(String userId);

    Optional<Member> findByRefreshToken(String refreshToken);

    Optional<Member> findByUserEmail(String userEmail);



}
