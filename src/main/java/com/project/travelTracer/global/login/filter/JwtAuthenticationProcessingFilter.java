package com.project.travelTracer.global.login.filter;

import com.project.travelTracer.global.jwt.service.JwtService;
import com.project.travelTracer.member.entity.Member;
import com.project.travelTracer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//JWT를 사용하여 인증 처리하는 커스텀 필터입니다.
@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {


    private final JwtService jwtService;    //JWT관련 기능 제공하는 서비스 객체
    private final MemberRepository memberRepository;    //회원 정보 조회하기 위한 리포지토리

    //권한 매핑 담당 객체(기본 NullAuthoritiesMapper 사용)
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    //인증을 확인하지 않는 URL 지정
    private final String NO_CHECK_URL = "/login";

    //실제로 필터링 작업 수행 메서드
    //로그인 요청인 경우 필터 체인 통과
    //그렇지 않는 경우 Access Token 및 인증 확인
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals(NO_CHECK_URL)){
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        if(refreshToken != null) {
            checkRefreshTokenAndReissueAccessToken(response, refreshToken);
            return;
        }

        checkAccessTokenAndAuthentication(request, response, filterChain);
    }

    //Refresh Token 확인, 해당 Refresh Token이 유효한 경우 새로운 Access Token 발급
    private void checkRefreshTokenAndReissueAccessToken(HttpServletResponse response, String refreshToken) {
        memberRepository.findByRefreshToken(refreshToken).ifPresent(
                member -> jwtService.sendAccessToken(response, jwtService.createAccessToken(member.getUserId()))
        );

    }
    //Access Token 확인 유요한 경우 Access Token에서 사용자 ID 추출하여 회원 정보 조회, 인증 후 저장
    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(request).filter(jwtService::isTokenValid).ifPresent(
                accessToken -> jwtService.extractUserId(accessToken).ifPresent(
                        userid -> memberRepository.findByUserId(userid).ifPresent(
                                member -> saveAuthentication(member)
                        )
                )
        );
        filterChain.doFilter(request,response);
    }

    //회원 정보 기반으로 UserDetails, Authentication 객체 생성
    //SecurityContext에 저장 => 인증된 사용자 정보 유지
    private void saveAuthentication(Member member) {
        UserDetails user = User.builder()
                .username(member.getUserId())
                .password(member.getUserPassword())
                .roles(member.getRole().name())
                .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authoritiesMapper.mapAuthorities(user.getAuthorities()));
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }


}
