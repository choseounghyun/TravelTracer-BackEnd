package com.project.travelTracer.Image.Service;

import com.project.travelTracer.Image.Entity.Image;
import com.project.travelTracer.Image.Repository.ImageRepository;
import com.project.travelTracer.Image.dto.ImageDto;
import com.project.travelTracer.Image.dto.ImageResponseDto;
import com.project.travelTracer.Image.exception.ImageException;
import com.project.travelTracer.Image.exception.ImageExceptionType;
import com.project.travelTracer.Post.entity.Post;
import com.project.travelTracer.Post.exception.PostException;
import com.project.travelTracer.Post.exception.PostExceptionType;
import com.project.travelTracer.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public ImageDto findById(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 파일 존재하지 않습니다"));

        ImageDto imageDto = ImageDto.builder()
                .originalFileName(image.getOriginFileName())
                .filePath(image.getFilePath())
                .fileSize(image.getFileSize())
                .build();
        return imageDto;
    }

    @Override
    public List<ImageResponseDto> findAllByPost(Long postId) {
        List<Image> imageList = imageRepository.findAllByPostId(postId);
        return imageList.stream().map(ImageResponseDto :: new).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {

        Image image = imageRepository.findById(id).orElseThrow(() ->
                new ImageException(ImageExceptionType.IMAGE_NOT_POUND));

        checkAuthority(image,PostExceptionType.NOT_AUTHORITY_DELETE_POST);

        imageRepository.delete(image);
    }

    private void checkAuthority(Image image, PostExceptionType postExceptionType) {
        if(!image.getPost().getWriter().getUserId().equals(SecurityUtil.getLoginUserId())) {
            throw new PostException(postExceptionType);
        }

    }
}
