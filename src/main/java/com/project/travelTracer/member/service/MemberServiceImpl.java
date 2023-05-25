package com.project.travelTracer.member.service;

import com.project.travelTracer.global.util.SecurityUtil;
import com.project.travelTracer.member.dto.FindIdDto;
import com.project.travelTracer.member.dto.MemberInfoDto;
import com.project.travelTracer.member.dto.MemberSignUpDto;
import com.project.travelTracer.member.dto.MemberUpdateDto;
import com.project.travelTracer.member.entity.Member;
import com.project.travelTracer.member.exception.MemberException;
import com.project.travelTracer.member.exception.MemberExceptionType;
import com.project.travelTracer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService
{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(MemberSignUpDto memberSignUpDto) throws Exception {
        Member member = memberSignUpDto.toEntity();
        member.addUserAuthority();
        member.encodePassword(passwordEncoder);

        if(memberRepository.findByUserId(memberSignUpDto.getUserId()).isPresent()){
            throw new MemberException(MemberExceptionType.ALREADY_EXIST_USERID);
        }

        memberRepository.save(member);
        log.info("save 메소드 실행");
    }

    @Override
    public void update(MemberUpdateDto memberUpdateDto) throws Exception {
        Member member = memberRepository.findByUserId(SecurityUtil.getLoginUserId()).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        memberUpdateDto.getAge().ifPresent(member::updateAge);
        memberUpdateDto.getUserName().ifPresent(member::updateName);
    }

    @Override
    public void updatePassword(String checkPassword, String toBePassword) throws Exception {
        Member member = memberRepository.findByUserId(SecurityUtil.getLoginUserId()).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        if(!member.matchPassword(passwordEncoder, checkPassword)) {
            throw new MemberException(MemberExceptionType.WRONG_PASSWORD);
        }

        member.updatePassword(passwordEncoder, toBePassword);
    }

    @Override
    public void withdraw(String checkPassword) throws Exception {
        Member member = memberRepository.findByUserId(SecurityUtil.getLoginUserId()).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        if(!member.matchPassword(passwordEncoder, checkPassword) ) {
            throw new MemberException(MemberExceptionType.WRONG_PASSWORD);
        }

        memberRepository.delete(member);
    }

    @Override
    public MemberInfoDto getInfo(Long id) throws Exception {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
        return new MemberInfoDto(findMember);
    }

    @Override
    public MemberInfoDto getMyInfo() throws Exception {
        Member findMember = memberRepository.findByUserId(SecurityUtil.getLoginUserId()).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
        return new MemberInfoDto(findMember);
    }

    @Override
    public boolean checkIdDuplicate(String userId) throws Exception {
        return memberRepository.existsByUserId(userId);
    }

    @Override
    public String findIdByEmail(String userEmail, String checkUserName) throws Exception {
        log.info("서비스의 메소드 실행");
        Optional<Member> optionalMember = memberRepository.findByUserEmail(userEmail);
        Member member = null;
        if (optionalMember.isPresent()) {
            member = optionalMember.get();
            if (member.getUserName().equals(checkUserName)) {
                return member.getUserId();
            }
            return null;
        }
        return null;
    }


}
