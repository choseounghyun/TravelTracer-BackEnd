package com.project.travelTracer.global.file.handler;

import com.project.travelTracer.Image.Entity.Image;
import com.project.travelTracer.Image.Service.ImageServiceImpl;
import com.project.travelTracer.Image.dto.ImageDto;
import com.project.travelTracer.Post.entity.Post;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileHandler {

    private final ImageServiceImpl imageService;

    public FileHandler(ImageServiceImpl imageService) {
        this.imageService = imageService;
    }

    public List<Image> parseFileInfo(Post post, List<MultipartFile> multipartFiles) throws Exception {
        //반환할 이미지 리스트
        List<Image> imageList = new ArrayList<>();

        //전달되어 온 파일이 존재할 경우
        if(!CollectionUtils.isEmpty(multipartFiles)) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            //프로젝트 디렉터리 내의 저장을 위한 절대 경로 설정
            //경로 구분자 File.separator 사용
            String absolutePath = new File("").getAbsolutePath() + File.separator;
            //파일을 저장할 세부 경로 지정
            String path = "images" + File.separator + current_date;
            File file = new File(path);

            boolean wasSuccessful = false;

            if (!file.exists()) {
                wasSuccessful = file.mkdirs();
            }

            if (!wasSuccessful) {
                System.out.println("file : was not successful");
            }
            //다중 파일 처리
            for(MultipartFile multipartFile : multipartFiles) {

                // 파일의 확장자 추출
                String originalFileExtension;
                String contentType = multipartFile.getContentType();

                //확장자 명이 존재하지 않을 경우 X
                if(ObjectUtils.isEmpty(contentType)) {
                    break;
                }
                else {
                    if(contentType.contains("image/jpeg")){
                        originalFileExtension = ".jpg";
                    }
                    else if( contentType.contains("image/png")) {
                        originalFileExtension = ".png";
                    }
                    else {
                        break;
                    }
                }

                String new_file_name = System.nanoTime() + originalFileExtension;

                ImageDto imageDto = ImageDto.builder()
                        .originalFileName(multipartFile.getOriginalFilename())
                        .filePath(path + File.separator + new_file_name)
                        .fileSize(multipartFile.getSize()).build();

                Image image = new Image(
                        imageDto.getOriginalFileName(),
                        imageDto.getFilePath(),
                        imageDto.getFileSize()
                );

                if(post.getId() != null) {
                    post.addImage(image);
                }

                imageList.add(image);

                //업로드 한 파일 데이터를 지정한 파일에 저장
                file = new File(absolutePath + path + File.separator + new_file_name);
                multipartFile.transferTo(file);

                file.setWritable(true);
                file.setReadable(true);
            }

        }
        return imageList;
    }
}
