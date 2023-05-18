package com.project.travelTracer.global.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {

    private int code;

    public CommonResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private String message;
    private T data;
}
