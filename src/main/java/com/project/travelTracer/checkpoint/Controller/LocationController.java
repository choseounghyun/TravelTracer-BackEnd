package com.project.travelTracer.checkpoint.Controller;

import com.project.travelTracer.checkpoint.dto.CheckPointDto;
import com.project.travelTracer.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    private final
    //체크 포인트 저장
    @PostMapping("/CheckpointManager")
    @ResponseStatus(HttpStatus.OK)
     ResponseEntity<CommonResponse> CheckpointManger(@Valid @RequestBody CheckPointDto CheckPointDto , BindingResult bindingResult) throws Exception{
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        //CheckPointService.
        return ResponseEntity.ok(new CommonResponse<>(200, "성공"));
    }
}
