package com.project.travelTracer.member.service;

import com.project.travelTracer.member.dto.FindIdDto;
import com.project.travelTracer.member.dto.MemberInfoDto;
import com.project.travelTracer.member.dto.MemberSignUpDto;
import com.project.travelTracer.member.dto.MemberUpdateDto;
import org.springframework.stereotype.Service;


public interface MemberService {

    /**
     * 회원가입, 회원수정, 비번변경, 정보조회
     */

    void signUp(MemberSignUpDto memberSignUpDto) throws Exception;

    void update(MemberUpdateDto memberUpdateDto) throws Exception;

    void updatePassword(String checkPassword, String toBePassword) throws Exception;

    void withdraw(String checkPassword) throws Exception;

    MemberInfoDto getInfo(Long id) throws Exception;

    MemberInfoDto getMyInfo() throws Exception;

    boolean checkIdDuplicate(String userId) throws Exception;

    String findIdByEmail(String userEmail, String checkUserName) throws Exception;
}
