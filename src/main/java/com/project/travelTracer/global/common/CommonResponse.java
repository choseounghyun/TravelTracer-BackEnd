package com.project.travelTracer.global.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResponse<T> {

    private int code;

    public CommonResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private String message;
    private T data;
}
