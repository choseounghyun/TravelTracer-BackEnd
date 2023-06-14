package com.project.travelTracer.Image.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageDto {

    private String originalFileName;
    private String filePath;
    private Long fileSize;

    @Builder
    public ImageDto(String originalFileName, String filePath, Long fileSize) {
        this.originalFileName = originalFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
