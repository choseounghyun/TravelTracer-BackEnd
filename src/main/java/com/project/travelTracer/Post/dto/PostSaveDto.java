package com.project.travelTracer.Post.dto;

import com.project.travelTracer.Post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveDto {

    @NotBlank(message = "제목을 입력해주세요")
    String title;

    @NotBlank(message = "내용을 입력해주세요")
    String content;

    @NotBlank(message = "주소를 입력해주세요")
    String address;

    List<MultipartFile> files;

    public Post toEntity() {
        return Post.builder().title(title).content(content).build();
    }
}
