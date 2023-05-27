package com.project.travelTracer.member.service;

import com.project.travelTracer.member.dto.EmailDto;
import com.project.travelTracer.member.dto.FindPasswordDto;
import com.project.travelTracer.member.entity.Member;
import com.project.travelTracer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public EmailDto createMailAndChangePassword(FindPasswordDto findPasswordDto) throws Exception {
        String tempPassword = getTempPassword();
        EmailDto emailDto = new EmailDto();
        emailDto.setAddress(findPasswordDto.getUserEmail());
        emailDto.setTitle("Travel Tracer 임시 비밀번호 안내 이메일 입니다");
        emailDto.setMessage("안녕하세요. Travel Tracer 임시 비밀번호 안내 관련 이메일 입니다." +
                 " 회원님의 임시 비밀번호는 " + tempPassword +" 입니다." + " 로그인 후에 비밀번호를 변경을 해주세요");
        Member member = memberRepository.findByUserId(findPasswordDto.getUserId()).get();
        member.updatePassword(passwordEncoder, tempPassword);
        log.info("tempPassword = {}", tempPassword);
        return emailDto;
    }

    public String getTempPassword() {
        char[] charSet = new char[] {
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
                'v', 'w','x','y','z'
        };
        char[] charSet2 = new char[] {
                'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };

        char[] numSet = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String tempPassword = "";

        int idx =0;
        for(int i=0; i<5; i++) {
            idx = (int) (charSet.length *Math.random());
            tempPassword += charSet[idx];
            idx = (int) (charSet2.length *Math.random());
            tempPassword += charSet2[idx];
            idx = (int) (numSet.length *Math.random());
            tempPassword += numSet[idx];
        }
        return tempPassword;
    }

    public void emailSend(EmailDto emailDto) {
        log.info("전송 완료");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDto.getAddress());
        message.setSubject(emailDto.getTitle());
        message.setText(emailDto.getMessage());
        message.setFrom("dldpcks34@naver.com");
        message.setReplyTo("dldpcks34@naver.com");
        javaMailSender.send(message);
    }

}
