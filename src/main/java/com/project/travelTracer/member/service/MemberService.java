package com.project.travelTracer.member.service;

import com.project.travelTracer.member.dto.MemberDTO;
import com.project.travelTracer.member.entity.MemberEntity;
import com.project.travelTracer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
        //중요한 건, 리포지토리에 저장하려면 엔티티객체를 넘겨야 한다 DTO X
    }

    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> memberLoginID = memberRepository.findByMemberLoginID(memberDTO.getMemberLoginID());
        if(memberLoginID.isPresent()) {
            //isPresent() 는 조회 결과가 있는지(null 값이 아닌지)
            MemberEntity memberEntity = memberLoginID.get();
            //들어온 이메일을 가진 정보의 비밀번호와 DB의 비밀번호가 일치하는 지 확인
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                //entity -> DTO로 변환
                return MemberDTO.toMemberDTO(memberEntity);
            }
            else {
                //비밀번호 불일치
                return null;
            }
        }
        else {
            //없는 이메일일때
            return null;
        }
    }
}
