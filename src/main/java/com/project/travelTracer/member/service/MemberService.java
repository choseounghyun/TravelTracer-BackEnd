package com.project.travelTracer.member.service;

import com.project.travelTracer.member.dto.MemberDTO;
import com.project.travelTracer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        System.out.println("Dddd");
    }
}
