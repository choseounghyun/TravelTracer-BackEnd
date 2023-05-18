package com.project.travelTracer.global.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonDetailResponse<T> {
    public T data;
}
