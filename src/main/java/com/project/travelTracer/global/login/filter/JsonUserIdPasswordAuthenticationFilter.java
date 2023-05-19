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

@Slf4j
public class JsonUserIdPasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_LOGIN_REQUEST_URL = "/login";

    private static final String HTTP_METHOD = "POST"; //post 방식

    private static final String CONTENT_TYPE = "application/json"; //콘턴트 타입은 json 파일

    private final ObjectMapper objectMapper;

    private static final String USERId_KEY = "userId";
    private static final String PASSWORD_KEY = "userPassword";

    private static final AntPathRequestMatcher DEFAULT_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD);

    public JsonUserIdPasswordAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_MATCHER);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("여기까지들어오나???");
        log.info("type= ",request.getContentType());
        log.info(request.getParameter("userId"));

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
