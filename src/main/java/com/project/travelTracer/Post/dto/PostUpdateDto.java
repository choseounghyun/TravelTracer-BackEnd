package com.project.travelTracer.Post.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
@NoArgsConstructor
public class PostUpdateDto {

    Optional<String> title;

    Optional<String> content;

    Optional<MultipartFile> uploadFile;
}
