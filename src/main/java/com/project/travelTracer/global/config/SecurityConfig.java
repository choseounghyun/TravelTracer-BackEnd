package com.project.travelTracer.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.travelTracer.global.jwt.service.JwtService;
import com.project.travelTracer.global.login.filter.JsonUserIdPasswordAuthenticationFilter;
import com.project.travelTracer.global.login.filter.JwtAuthenticationProcessingFilter;
import com.project.travelTracer.global.login.handler.LoginFailureHandler;
import com.project.travelTracer.global.login.handler.LoginSuccessJWTProvideHandler;
import com.project.travelTracer.member.repository.MemberRepository;
import com.project.travelTracer.member.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@RequiredArgsConstructor  //의존성 주입
public class SecurityConfig {

    private final LoginService loginService;
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;


    //filterChain()은 SecurityFilterChain 반환, Spring Security 필터 체인 구성
    @Bean  //Spring Bean으로 등록
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .formLogin().disable()
                .httpBasic().disable()          //HTTP 기본 인증을 비활성화
                .csrf().disable()               //CSRF 공격 방지 기능 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //서버에 세션 유지 x, 클라이언트의 모든 요청을 인증 토큰에 의존

                .and()
                .authorizeRequests()//특정 경로에 대한 접근 허용 및 제안 login, signUp, check, findId, findPw 제외하고 나머지 경로 인증 필요
                .antMatchers("/login", "/signUp", "/", "/check/**", "/findId", "/findPw").permitAll()
                .anyRequest().authenticated();
        http.addFilterAfter(jsonUsernamePasswordFilter(), LogoutFilter.class);  // 커스텀 필터 틍록 (JSON 형식의 사용자 이름, 비밀번호 인증)
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), JsonUserIdPasswordAuthenticationFilter.class); //jwtAuthenticationProcessingFilter()는 JWT 인증을 처리하는 커스텀 필터
        return http.build();
    }

    //Spring Security에서 사용할 패스워드 인코더 설정
    //PasswordEncoderFactories.createDelegatingPasswordEncoder()를 호출
    //다양한 알고리즘을 지원하는 패스워드 인코더를 생성
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //인증매니저 구성
    //DaoAuthenticationProvider를 사용하여 인증 과정에 필요한 정보를 설정하고, 생성된 인증 매니저에 등록
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(loginService);
        return new ProviderManager(provider);
    }

    //loginSuccessJWTProvideHandler()와 loginFailureHandler()
    //로그인 성공 및 실패 핸들러를 생성
    //이 핸들러는 로그인 요청의 결과에 따라 적절한 응답을 제공
    @Bean
    public LoginSuccessJWTProvideHandler loginSuccessJWTProvideHandler() {
        return new LoginSuccessJWTProvideHandler(jwtService, memberRepository);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }


    //JSON 형식의 사용자 이름과 비밀번호를 처리하는 필터를 생성
    //필터는 authenticationManager()를 설정하고, 인증 성공 및 실패 핸들러를 등록
    @Bean
    public JsonUserIdPasswordAuthenticationFilter jsonUsernamePasswordFilter() {
        JsonUserIdPasswordAuthenticationFilter jsonUsernamePasswordFilter = new JsonUserIdPasswordAuthenticationFilter(objectMapper);
        jsonUsernamePasswordFilter.setAuthenticationManager(authenticationManager());
        jsonUsernamePasswordFilter.setAuthenticationSuccessHandler(loginSuccessJWTProvideHandler());
        jsonUsernamePasswordFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return jsonUsernamePasswordFilter;
    }

    //jwtAuthenticationProcessingFilter()는 JWT 인증 처리를 위한 필터를 생성
    //필터는 JWT 서비스와 회원 리포지토리를 주입받고 사용
    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter(){
        JwtAuthenticationProcessingFilter jsonUserIdPasswordLoginFilter = new JwtAuthenticationProcessingFilter(jwtService, memberRepository);
        return jsonUserIdPasswordLoginFilter;
    }
}
