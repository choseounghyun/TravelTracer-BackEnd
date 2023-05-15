package com.project.travelTracer.member.repository;

import com.project.travelTracer.member.entity.Member;
import com.project.travelTracer.member.entity.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private void clear() {
        em.flush();
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
    public void join_notHaving_id() throws Exception{
        Member member = Member.builder().
                userPassword("3251840aa!").
                userName("이예찬").
                age(29).
                role(Role.USER).build();

        assertThrows(Exception.class, () -> memberRepository.save(member));
    }

    //중복 아이가 있는지 테스트
    @Test
    public void duplicated_id() throws Exception{
        Member member1 = Member.builder().
                userId("userId").
                userPassword("3251840aa!").
                userName("이예찬").
                age(29).
                role(Role.USER).build();

        Member member2 = Member.builder().
                userId("userId").
                userPassword("11111!").
                userName("이명석").
                age(26).
                role(Role.USER).build();

        memberRepository.save(member1);
        clear();
        assertThrows(Exception.class, () -> memberRepository.save(member2));
    }

    //회원 수정 테스트
    @Test
    public void update() throws Exception {
        Member member1 = Member.builder().
                userId("userId").
                userPassword("3251840aa!").
                userName("이예찬").
                age(29).
                role(Role.USER).build();

        memberRepository.save(member1);
        clear();

        String updatePassword = "123456";
        String updateName = "조승현";
        int updateAge = 26;

        //비밀번호 암호화 메소드 제공
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Member findMember = memberRepository.findById(member1.getId()).orElseThrow(() -> new Exception());
        findMember.updateAge(updateAge);
        findMember.updateName(updateName);
        findMember.updatePassword(passwordEncoder, updatePassword);
        em.flush();

        Member updateMember = memberRepository.findById(findMember.getId()).orElseThrow(() -> new Exception());
        assertThat(updateMember).isEqualTo(findMember); //변경한 회원과 저장된 회원이 같은 주소를 가르키는 지
        assertThat(passwordEncoder.matches(updatePassword, updateMember.getUserPassword())).isTrue(); //암호화하지 않은 비밀번호와 암호화한 비밀번호가 같은지
        assertThat(updateMember.getUserName()).isEqualTo(updateName);

    }


}