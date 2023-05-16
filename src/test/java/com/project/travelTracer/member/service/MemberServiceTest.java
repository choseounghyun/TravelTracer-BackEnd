package com.project.travelTracer.member.service;


import com.project.travelTracer.member.dto.MemberSignUpDto;
import com.project.travelTracer.member.dto.MemberUpdateDto;
import com.project.travelTracer.member.entity.Member;
import com.project.travelTracer.member.entity.Role;
import com.project.travelTracer.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    String PASSWORD = "password";

    private void clear(){
        em.flush();
        em.clear();
    }

    private MemberSignUpDto makeMemberSignUpDto() {
        return new MemberSignUpDto("dldpcks11", PASSWORD, "lee", 29);
    }

    private MemberSignUpDto setMember() throws Exception {
        MemberSignUpDto memberSignUpDto = makeMemberSignUpDto();
        memberService.signUp(memberSignUpDto);
        clear();
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();

        emptyContext.setAuthentication(new UsernamePasswordAuthenticationToken(User.builder()
                .username(memberSignUpDto.getUserId())
                .password(memberSignUpDto.getUserPassword())
                .roles(Role.USER.name())
                .build(),
                null, null));

        SecurityContextHolder.setContext(emptyContext);
        return  memberSignUpDto;
    }

    @AfterEach
    public void removeMember(){
        SecurityContextHolder.createEmptyContext().setAuthentication(null);
    }

    @Test
    public void 회원가입_성공() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto = makeMemberSignUpDto();

        //when
        memberService.signUp(memberSignUpDto);
        clear();

        Member member = memberRepository.findByUserId(memberSignUpDto.getUserId()).orElseThrow(() -> new Exception("회원이 없습니다"));
        assertThat(member.getId()).isNotNull();
        assertThat(member.getUserId()).isEqualTo(memberSignUpDto.getUserId());
        assertThat(member.getUserName()).isEqualTo(memberSignUpDto.getUserName());
        assertThat(member.getAge()).isEqualTo(memberSignUpDto.getAge());
        assertThat(member.getRole()).isSameAs(Role.USER);
    }

    @Test
    public void join_fail_id() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto = makeMemberSignUpDto();
        memberService.signUp(memberSignUpDto);
        clear();

        //when, then TODO : MemberException으로 고쳐야 함
        assertThat(assertThrows(Exception.class, () -> memberService.signUp(memberSignUpDto)).getMessage()).isEqualTo("이미 존재하는 회원입니다");

    }

    @Test
    public void none_insert() throws Exception {
        MemberSignUpDto memberSignUpDto1 = new MemberSignUpDto("dldpcks11", PASSWORD, "lee", null);
        MemberSignUpDto memberSignUpDto2 = new MemberSignUpDto("dldpcks11", PASSWORD, null, 29);
        MemberSignUpDto memberSignUpDto3 = new MemberSignUpDto("dldpcks11", null, "lee", 29);
        MemberSignUpDto memberSignUpDto4 = new MemberSignUpDto(null, PASSWORD, "lee", 29);

        assertThrows(Exception.class, () -> memberService.signUp(memberSignUpDto1));

        assertThrows(Exception.class, () -> memberService.signUp(memberSignUpDto2));

        assertThrows(Exception.class, () -> memberService.signUp(memberSignUpDto3));

        assertThrows(Exception.class, () -> memberService.signUp(memberSignUpDto4));
    }

    @Test
    public void password_change() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto = setMember();


        //when
        String toBePassword = "1234567890!@#!@#";
        memberService.updatePassword(PASSWORD, toBePassword);
        clear();

        //then
        Member findMember = memberRepository.findByUserId(memberSignUpDto.getUserId()).orElseThrow(() -> new Exception());
        assertThat(findMember.matchPassword(passwordEncoder, toBePassword)).isTrue();

    }
    @Test
    public void update_NameAge() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto = setMember();

        //when
        Integer updateAge = 33;
        String updateName = "변경할래용";
        memberService.update(new MemberUpdateDto(Optional.of(updateName), Optional.of(updateAge)));
        clear();

        //then
        memberRepository.findByUserId(memberSignUpDto.getUserId()).ifPresent((member -> {
            assertThat(member.getAge()).isEqualTo(updateAge);
            assertThat(member.getUserName()).isEqualTo(updateName);

        }));
    }

    @Test
    public void withdraw() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto = setMember();

        //when
        memberService.withdraw(PASSWORD);

        //then
        assertThat(assertThrows(Exception.class, ()-> memberRepository.findByUserId(memberSignUpDto.getUserId()).orElseThrow(() -> new Exception("회원이 없습니다"))).getMessage()).isEqualTo("회원이 없습니다");

    }

    @Test
    public void 회원탈퇴_실패_비밀번호가_일치하지않음() throws Exception {
        //given
        MemberSignUpDto memberSignUpDto = setMember();

        //when, then TODO : MemberException으로 고쳐야 함
        assertThat(assertThrows(Exception.class ,() -> memberService.withdraw(PASSWORD+"1")).getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.");

    }
}
