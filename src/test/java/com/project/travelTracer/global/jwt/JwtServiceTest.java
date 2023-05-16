package com.project.travelTracer.global.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.travelTracer.global.jwt.service.JwtService;
import com.project.travelTracer.member.entity.Member;
import com.project.travelTracer.member.entity.Role;
import com.project.travelTracer.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
public class JwtServiceTest {

    @Autowired
    JwtService jwtService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access.header}")
    private String accessHeader;
    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN = "AccessToken";
    private static final String REFRESH_TOKEN = "RefreshToken";
    private static final String USERID_CLAIM = "userId";
    private static final String BEARER = "Bearer ";

    private String userId = "userId";

    @BeforeEach
    public void init() {
        memberRepository.save(Member.builder()
                .userId(userId)
                .userPassword("325184dd")
                .userName("Member1")
                .role(Role.USER)
                .age(22)
                .build());
        clear();
    }

    public void clear() {
        em.flush();
        em.clear();
    }

    private DecodedJWT getVerfiy(String token) {
        return JWT.require(Algorithm.HMAC512(secret)).build().verify(token);
    }

    //accessToken 발급 테스트
    @Test
    public void createAccessToken_AccessToken() throws Exception {
        String accessToken = jwtService.createAccessToken(userId);

        DecodedJWT verfiy = getVerfiy(accessToken);

        String subject = verfiy.getSubject();
        String findUserId = verfiy.getClaim(USERID_CLAIM).asString();

        assertThat(findUserId).isEqualTo(userId);
        assertThat(subject).isEqualTo(ACCESS_TOKEN);
    }

    @Test
    public void createRefreshToken_RefreshToken() throws Exception {
        String refreshToken = jwtService.createRefreshToken();
        DecodedJWT verfiy = getVerfiy(refreshToken);
        String subject = verfiy.getSubject();
        String userId = verfiy.getClaim(USERID_CLAIM).asString();

        assertThat(subject).isEqualTo(REFRESH_TOKEN);
        assertThat(userId).isNull();
    }

    @Test
    public void updateRefreshToken_RefreshToken() throws  Exception {
        String refreshToken = jwtService.createRefreshToken();
        jwtService.updateRefreshToken(userId,refreshToken);
        clear();
        Thread.sleep(3000);

        String reissuedRefreshToken = jwtService.createRefreshToken();
        jwtService.updateRefreshToken(userId, reissuedRefreshToken);

        assertThrows(Exception.class, ()-> memberRepository.findByRefreshToken(refreshToken).get());
        assertThat(memberRepository.findByRefreshToken(reissuedRefreshToken).get().getUserId()).isEqualTo(userId);
    }

    @Test
    public void destroyRefreshToken() throws Exception{
        String refreshToken = jwtService.createRefreshToken();
        jwtService.updateRefreshToken(userId, refreshToken);
        clear();

        jwtService.destroyRefreshToken(userId);
        clear();

        assertThrows(Exception.class, () -> memberRepository.findByRefreshToken(refreshToken).get());

        Member member = memberRepository.findByUserId(userId).get();
        assertThat(member.getRefreshToken()).isNull();
    }
}
