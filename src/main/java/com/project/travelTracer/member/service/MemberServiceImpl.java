package com.project.travelTracer.member.service;

import com.project.travelTracer.global.util.SecurityUtil;
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
public class MemberServiceImpl implements MemberService //MemberSericie 인터페이스 구현
{
    //데이터베이스 작업과 비밀번호 암호화 수행
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //회원 가입 처리
    @Override
    public void signUp(MemberSignUpDto memberSignUpDto) throws Exception {
        Member member = memberSignUpDto.toEntity();     //엔티티 변환
        member.addUserAuthority();                      //권한 추가
        member.encodePassword(passwordEncoder);         //비밀번호 암호화 후 저장

        if(memberRepository.findByUserId(memberSignUpDto.getUserId()).isPresent()){
            throw new MemberException(MemberExceptionType.ALREADY_EXIST_USERID);
        }

        memberRepository.save(member);
        log.info("save 메소드 실행");
    }

    //회원 정보 업데이트 처리
    @Override
    public void update(MemberUpdateDto memberUpdateDto) throws Exception {
        Member member = memberRepository.findByUserId(SecurityUtil.getLoginUserId()).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        memberUpdateDto.getAge().ifPresent(member::updateAge);
        memberUpdateDto.getUserName().ifPresent(member::updateName);
        memberUpdateDto.getUserEmail().ifPresent(member::updateEmail);
    }

    //비밀번호 확인
    @Override
    public void updatePassword(String checkPassword, String toBePassword) throws Exception {
        Member member = memberRepository.findByUserId(SecurityUtil.getLoginUserId()).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        if(!member.matchPassword(passwordEncoder, checkPassword)) {
            throw new MemberException(MemberExceptionType.WRONG_PASSWORD);
        }

        member.updatePassword(passwordEncoder, toBePassword);
    }

    //회원 탈퇴 처리
    @Override
    public void withdraw(String checkPassword) throws Exception {
        Member member = memberRepository.findByUserId(SecurityUtil.getLoginUserId()).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));

        if(!member.matchPassword(passwordEncoder, checkPassword) ) {
            throw new MemberException(MemberExceptionType.WRONG_PASSWORD);
        }

        memberRepository.delete(member);
    }

    // 지정된 회원 정보 조회 -> MemberInfoDto로 반환
    @Override
    public MemberInfoDto getInfo(Long id) throws Exception {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
        return new MemberInfoDto(findMember);
    }

    // 현재 로드인된 사용자 정보 조회
    @Override
    public MemberInfoDto getMyInfo() throws Exception {
        Member findMember = memberRepository.findByUserId(SecurityUtil.getLoginUserId()).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
        return new MemberInfoDto(findMember);
    }

    //입력받은 사용자 아이디 유무 확인
    @Override
    public boolean checkIdDuplicate(String userId) throws Exception {
        return memberRepository.existsByUserId(userId);
    }

    //입력받은 이메일과 사용자 이름 기반으로 아이디 찾기
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

    //사용자 아이디, 이메일, 사용자 이름 일치 확인
    @Override
    public boolean userCheck(String userId, String userEmail, String userName) throws Exception {
        Optional<Member> optionalMember = memberRepository.findByUserEmail(userEmail);
        Member member = null;
        if (optionalMember.isPresent()) {
            member = optionalMember.get();
            if(member.getUserId().equals(userId) && member.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }


}
