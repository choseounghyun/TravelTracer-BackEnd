package com.project.travelTracer.member.service;

import com.project.travelTracer.global.util.SecurityUtil;
import com.project.travelTracer.member.dto.MemberInfoDto;
import com.project.travelTracer.member.dto.MemberSignUpDto;
import com.project.travelTracer.member.dto.MemberUpdateDto;
import com.project.travelTracer.member.entity.Member;
import com.project.travelTracer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ExpressionException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(MemberSignUpDto memberSignUpDto) throws Exception {
        Member member = memberSignUpDto.toEntity();
        member.addUserAuthority();
        member.encodePassword(passwordEncoder);

        log.info("여까지들어옴");
        if(memberRepository.findByUserId(memberSignUpDto.getUserId()).isPresent()){
            throw new Exception("이미 존재하는 회원입니다");
        }

        memberRepository.save(member);
        log.info("save 메소드 실행");
    }

    @Override
    public void update(MemberUpdateDto memberUpdateDto) throws Exception {
        Member member = memberRepository.findByUserId(SecurityUtil.getLoginUserId()).orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));

        memberUpdateDto.getAge().ifPresent(member::updateAge);
        memberUpdateDto.getUserName().ifPresent(member::updateName);
    }

    @Override
    public void updatePassword(String checkPassword, String toBePassword) throws Exception {
        Member member = memberRepository.findByUserId(SecurityUtil.getLoginUserId()).orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));

        if(!member.matchPassword(passwordEncoder, checkPassword)) {
            throw new Exception("비밀번호가 일치하지 않습니다");

        }

        member.updatePassword(passwordEncoder, toBePassword);
    }

    @Override
    public void withdraw(String checkPassword) throws Exception {
        Member member = memberRepository.findByUserId(SecurityUtil.getLoginUserId()).orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));

        if(!member.matchPassword(passwordEncoder, checkPassword) ) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        memberRepository.delete(member);
    }

    @Override
    public MemberInfoDto getInfo(Long id) throws Exception {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new Exception("회원이 없습니다"));
        return new MemberInfoDto(findMember);
    }

    @Override
    public MemberInfoDto getMyInfo() throws Exception {
        Member findMember = memberRepository.findByUserId(SecurityUtil.getLoginUserId()).orElseThrow(() -> new Exception("회원이 없습니다"));
        return new MemberInfoDto(findMember);
    }
}
