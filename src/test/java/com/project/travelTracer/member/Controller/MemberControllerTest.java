package com.project.travelTracer.member.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.travelTracer.member.dto.MemberSignUpDto;
import com.project.travelTracer.member.entity.Member;
import com.project.travelTracer.member.repository.MemberRepository;
import com.project.travelTracer.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    EntityManager em;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    PasswordEncoder passwordEncoder;


    private static String SIGN_UP_URL = "/signUp";

    private String userId = "dldpcks345";
    private String password = "password1234@";
    private String name = "LeeYC";
    private Integer age = 29;

    private void clear(){
        em.flush();
        em.clear();
    }

    private void signUp(String signUpData) throws Exception {
        mockMvc.perform(
                        post(SIGN_UP_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(signUpData))
                .andExpect(status().isOk());
    }


    @Value("${jwt.access.header}")
    private String accessHeader;

    private static final String BEARER = "Bearer ";

    private String getAccessToken() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("userId",userId);
        map.put("userPassword",password);


        MvcResult result = mockMvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isOk()).andReturn();

        return result.getResponse().getHeader(accessHeader);
    }

    @Test
    public void join() throws Exception {
        //given
        String signUpData = objectMapper.writeValueAsString(new MemberSignUpDto(userId, password, name, age));

        //when
        signUp(signUpData);

        //then
        Member member = memberRepository.findByUserId(userId).orElseThrow(() -> new Exception("회원이 없습니다"));
        assertThat(member.getUserName()).isEqualTo(name);
        assertThat(memberRepository.findAll().size()).isEqualTo(1);
    }
}


