package com.project.travelTracer.location.Controller;

import com.project.travelTracer.global.jwt.service.JwtService;
import com.project.travelTracer.global.login.handler.LoginSuccessJWTProvideHandler;
import com.project.travelTracer.location.dto.CheckPointDto;
import com.project.travelTracer.global.common.CommonResponse;
import com.project.travelTracer.location.service.CheckPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j

public class LocationController {

    private final CheckPointService checkpointService;
    private final JwtService jwtService; // JwtService 주입

    //체크 포인트 저장
    @PostMapping("/CheckpointManager")
    @ResponseStatus(HttpStatus.OK)
     ResponseEntity<CommonResponse> CheckPointSave(
             @Valid @RequestBody CheckPointDto CheckPointDto,
             BindingResult bindingResult,
             Authentication authentication,
             HttpServletRequest request,
             @AuthenticationPrincipal UserDetails userDetails) throws Exception{

         // userDetails 객체를 사용하여 현재 인증된 사용자 정보 활용
         String username = userDetails.getUsername();

        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        // 클라이언트로부터 토큰 추출
        Optional<String> accessToken = jwtService.extractAccessToken(request);

        // 토큰 검증
        if (!accessToken.isPresent() || !jwtService.isTokenValid(accessToken.get())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CommonResponse<>(403, "토큰이 유효하지 않습니다."));
        }

        // 토큰에서 사용자 아이디 추출
        Optional<String> userId = jwtService.extractUserId(accessToken.get());

        if (!userId.isPresent()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CommonResponse<>(403, "사용자 아이디를 추출할 수 없습니다."));
        }


        log.info("ingo CheckPointManagerDto={}", CheckPointDto);
        checkpointService.CheckpointManager(CheckPointDto);
        //CheckPointService.
        return ResponseEntity.ok(new CommonResponse<>(200, "성공"));
    }
}
