package com.project.travelTracer.location.Controller;

import com.project.travelTracer.global.jwt.service.JwtService;
import com.project.travelTracer.location.dto.CheckPointDto;
import com.project.travelTracer.global.common.CommonResponse;
import com.project.travelTracer.location.service.CheckPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.CountingMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
@Slf4j
public class LocationController {

    private final CheckPointService checkpointService;
    private final JwtService jwtService; // JwtService 주입

    @Autowired
    public LocationController(CheckPointService checkpointService, JwtService jwtService) {
        this.checkpointService = checkpointService;
        this.jwtService = jwtService;
    }

    //체크 포인트 저장
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/CheckpointManager")
    ResponseEntity<CommonResponse> CheckPointSave(
            @Valid @RequestBody CheckPointDto CheckPointDto,BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        log.info("ingo CheckPointManagerDto={}", CheckPointDto);
        checkpointService.CheckpointManager(CheckPointDto);
        return ResponseEntity.ok(new CommonResponse<>(200, "성공"));
    }
}

