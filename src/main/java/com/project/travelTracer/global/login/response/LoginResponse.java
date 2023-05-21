package com.project.travelTracer.global.login.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {

    private int code;

    public LoginResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private String message;

}
