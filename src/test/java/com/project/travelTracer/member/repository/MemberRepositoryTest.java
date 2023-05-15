package com.project.travelTracer.member.repository;

import com.project.travelTracer.member.entity.Member;
import com.project.travelTracer.member.entity.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Temporal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @AfterEach
    private void after(){
        em.clear();
    }

    //회원가입 테스트
    @Test
    public void join() throws Exception {
        Member member = Member.builder().
                userId("userId").
                userPassword("3251840aa!").
                userName("이예찬").
                age(29).
                role(Role.USER).build();

        Member saveMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(saveMember.getId())
                .orElseThrow(() -> new RuntimeException("저장된 회원이 없습니다"));

        assertThat(findMember).isEqualTo(saveMember);
        assertThat(findMember).isSameAs(member);

    }

    //회원 가입시 아이디가 없을 때
    @Test
    public void join_notHavingId() throws Exception{
        Member member = Member.builder().
                userPassword("3251840aa!").
                userName("이예찬").
                age(29).
                role(Role.USER).build();

        assertThrows(Exception.class, () -> memberRepository.save(member));
    }
}