package com.project.travelTracer.Image.dto;

import com.project.travelTracer.Image.Entity.Image;
import lombok.Getter;

@Getter
public class ImageResponseDto {

    private Long Id;

    public ImageResponseDto(Image image) {
        this.Id = image.getId();
    }
}
