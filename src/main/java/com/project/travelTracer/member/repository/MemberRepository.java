package com.project.travelTracer.member.repository;

import com.project.travelTracer.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Cacheable;
import java.util.Optional;

 //Spring Data JPA에서 사용되는 MemberRepository 인터페이스
public interface MemberRepository extends JpaRepository<Member, Long> {

    //userId를 기준으로 회원을 검색하는 메서드입니다.
    Optional<Member> findByUserId(String userId);

    //userId를 기준으로 회원의 존재 여부를 확인하는 메서드
    boolean existsByUserId(String userId);

    // refreshToken을 기준으로 회원을 검색하는 메서드
    Optional<Member> findByRefreshToken(String refreshToken);

    //userEmail을 기준으로 회원을 검색하는 메서드
    Optional<Member> findByUserEmail(String userEmail);

}
