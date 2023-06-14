package com.project.travelTracer.Post.controller;

import com.project.travelTracer.Image.Entity.Image;
import com.project.travelTracer.Image.Service.ImageService;
import com.project.travelTracer.Image.dto.ImageDto;
import com.project.travelTracer.Image.dto.ImageResponseDto;
import com.project.travelTracer.Post.dto.PostSaveDto;
import com.project.travelTracer.Post.dto.PostUpdateDto;
import com.project.travelTracer.Post.service.PostService;
import com.project.travelTracer.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.CollectionUtils.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final ImageService imageService;

    //게시글 저장
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post")
    public ResponseEntity<CommonResponse> save(@Valid @ModelAttribute PostSaveDto postSaveDto) throws Exception {

        postService.save(postSaveDto);
        return ResponseEntity.ok(new CommonResponse<>(200, "성공"));
    }

    //게시글 수정
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/post/{postId}")
    public void update(@PathVariable("postId") Long postId, @ModelAttribute PostUpdateDto postUpdateDto) throws Exception {
        //db에 저장된 이미지 불러오기
        List<ImageResponseDto> dbImageList = imageService.findAllByPost(postId);

        //전달 받은 파일들
        List<MultipartFile> uploadImages = postUpdateDto.getUploadFiles();

        //새롭게 전달 받은 파일들 목록을 저장할 리스트 선언
        List<MultipartFile> addImageList = new ArrayList<>();

        if(isEmpty(dbImageList)) { //db에 아에 존재하지 않는다면
            if(!isEmpty(uploadImages)) { //전달되어온 파일이 하나라도 존재한다면
                for( MultipartFile uploadImage : uploadImages) {
                    addImageList.add(uploadImage);
                }
            }
        }
        else {
            if(isEmpty(uploadImages)){  //전달되어온 파일이 없다면 파일삭제 고고
                for(ImageResponseDto image : dbImageList) {
                    imageService.delete(image.getId());
                }
            }
            else { //전달되어온 파일이 한장이상 존재
                List<String> dbOriginalNameList = new ArrayList<>(); //DB에 저장되어 있는 파일 원본명 목록
                for(ImageResponseDto image : dbImageList) { //db의 파일 원본명 추출
                    String dbOriginalFileName = imageService.findById(image.getId()).getOriginalFileName();

                    if(!uploadImages.contains(dbOriginalFileName)) { //서버에 저장된 이미지 중 전달되어온 이미지가 존재하지 않으면
                        imageService.delete(image.getId()); //이미지 삭제
                    }
                    else{
                        dbOriginalNameList.add(dbOriginalFileName);
                    }
                }
                for(MultipartFile multipartFile : uploadImages) { //전달되어온 파일 하나씩 검사
                    String multipartOriginalName = multipartFile.getOriginalFilename();
                    if(!dbOriginalNameList.contains(multipartOriginalName)) { //db에 없는 파일이면
                        addImageList.add(multipartFile); //db에 저장할 파일 목록 추가
                    }
                }
            }
        }
        postService.update(postId, postUpdateDto, addImageList);
    }


}
