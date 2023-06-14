package com.project.travelTracer.Post.service;


import com.project.travelTracer.Post.condition.PostSearchCondition;
import com.project.travelTracer.Post.dto.PostInfoDto;
import com.project.travelTracer.Post.dto.PostPagingDto;
import com.project.travelTracer.Post.dto.PostSaveDto;
import com.project.travelTracer.Post.dto.PostUpdateDto;
import com.project.travelTracer.global.file.exception.FileException;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    void save(PostSaveDto postSaveDto) throws Exception;

    void update(Long id, PostUpdateDto postUpdateDto, List<MultipartFile> files) throws Exception;

    void delete(Long id);

    PostInfoDto getPostInfo(Long id);

    PostPagingDto getPostList(Pageable pageable, PostSearchCondition postSearchCondition);


}
