package com.project.travelTracer.global.login.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

//JSON 형식의 사용자 이름과 비밀번호 처리 Spring Security 필터
//AbstractAuthenticationProcessingFilter를 상속
//인증 요청을 처리하는 메서드 구현
@Slf4j
public class JsonUserIdPasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    //DEFAULT_LOGIN_REQUEST_URL은 로그인 요청의 기본 URL을 지정
    //기본값 /login
    private static final String DEFAULT_LOGIN_REQUEST_URL = "/login";

    //로그인 요청의 HTTP메서드 지정(기본 POST방식 사용)
    private static final String HTTP_METHOD = "POST"; //post 방식
    //로그인 요청의 콘텐츠 타입 지정(기본값 application/json
    private static final String CONTENT_TYPE = "application/json"; //콘턴트 타입은 json 파일
    //JSON 데이터를 파싱하는 데 사용되는 ObjectMapper 객체
    private final ObjectMapper objectMapper;
    //JSON 데이터에서 사용자 이름(아이디),비밀번호 추출을 위한 키 값
    private static final String USERId_KEY = "userId";
    private static final String PASSWORD_KEY = "userPassword";

    private static final AntPathRequestMatcher DEFAULT_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD);

    //objectMapper를 주입 받아 상위 클래스의 생성자에 매개변수로 "DEFAULT_MATCHER" 전달
    //JsonUserIdPasswordAuthenticationFilter
    //Spring Security의 AbstractAuthenticationProcessingFilter를 확장
    //JSON 형식의 사용자 이름과 비밀번호를 처리하는 커스텀 필터를 구현
    //이 필터는 로그인 요청을 캡처하고, JSON 데이터를 추출하여 인증을 수행
    public JsonUserIdPasswordAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_MATCHER);
        this.objectMapper = objectMapper;
    }
    //인증 시도하는 메서드
    //실제 로그인 요청을 처리
    //메서드 내부 요청의 콘텐츠 타입 확인
    //JSON 데이터 추출해 사용자 이름(아이디),비밀 번호 추출
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("여기까지들어오나???");
        log.info("type= ",request.getContentType());
        log.info(request.getParameter("userId"));
        log.info(request.getParameter("userPassword"));

        if(request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)  ) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }
        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        Map<String, String> userIdPasswordMap = objectMapper.readValue(messageBody, Map.class);

        String userId = userIdPasswordMap.get(USERId_KEY);
        String password = userIdPasswordMap.get(PASSWORD_KEY);

        log.info("userid = {}" , userId);
        log.info("password = {}" , password);


        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userId, password);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
