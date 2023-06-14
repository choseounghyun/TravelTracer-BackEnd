package com.project.travelTracer.Image.Service;

import com.project.travelTracer.Image.dto.ImageDto;
import com.project.travelTracer.Image.dto.ImageResponseDto;

import java.util.List;

public interface ImageService {

    public ImageDto findById(Long id);

    public List<ImageResponseDto> findAllByPost(Long postId);

    public void delete(Long id);
}
