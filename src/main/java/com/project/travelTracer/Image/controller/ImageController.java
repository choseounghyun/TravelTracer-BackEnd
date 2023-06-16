package com.project.travelTracer.Image.controller;

import com.project.travelTracer.Image.Service.ImageService;
import com.project.travelTracer.Image.Service.ImageServiceImpl;
import com.project.travelTracer.Image.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    //썸네일요 ㅇ이미지 조회
    @CrossOrigin
    @GetMapping(value = "/thumbnail/{id}",
                produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getThumbnail(@PathVariable Long id) throws IOException {
        String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;
        String path;
        
        if(id!=0) { //전달되어 온 이미지가 기본 썸네일이 아닐 경우
            ImageDto imageDto = imageService.findById(id);
            path = imageDto.getFilePath();
        }
        else {
            path = "images" + File.separator + "thumbnail" + File.separator + "thumbnail.png";
        }

        InputStream imageStream = new FileInputStream(absolutePath + path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/image/{id}",
                produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
        ImageDto imageDto = imageService.findById(id);
        String absolutePath = new File("").getAbsolutePath() + File.separator;
        String path = imageDto.getFilePath();

        InputStream imageStream = new FileInputStream(absolutePath + path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }
 }
