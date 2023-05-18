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
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginService loginService;
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/login", "/signUp", "/", "/check/**").permitAll()
                .anyRequest().authenticated();
        http.addFilterAfter(jsonUsernamePasswordFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), JsonUserIdPasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(loginService);
        return new ProviderManager(provider);
    }

    @Bean
    public LoginSuccessJWTProvideHandler loginSuccessJWTProvideHandler() {
        return new LoginSuccessJWTProvideHandler(jwtService, memberRepository);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }


    @Bean
    public JsonUserIdPasswordAuthenticationFilter jsonUsernamePasswordFilter() {
        JsonUserIdPasswordAuthenticationFilter jsonUsernamePasswordFilter = new JsonUserIdPasswordAuthenticationFilter(objectMapper);
        jsonUsernamePasswordFilter.setAuthenticationManager(authenticationManager());
        jsonUsernamePasswordFilter.setAuthenticationSuccessHandler(loginSuccessJWTProvideHandler());
        jsonUsernamePasswordFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return jsonUsernamePasswordFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter(){
        JwtAuthenticationProcessingFilter jsonUserIdPasswordLoginFilter = new JwtAuthenticationProcessingFilter(jwtService, memberRepository);
        return jsonUserIdPasswordLoginFilter;
    }
}
