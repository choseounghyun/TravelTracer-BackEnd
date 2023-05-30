package com.project.travelTracer.Post.service;

import com.project.travelTracer.Post.condition.PostSearchCondition;
import com.project.travelTracer.Post.dto.PostInfoDto;
import com.project.travelTracer.Post.dto.PostPagingDto;
import com.project.travelTracer.Post.dto.PostSaveDto;
import com.project.travelTracer.Post.dto.PostUpdateDto;
import com.project.travelTracer.Post.entity.Post;
import com.project.travelTracer.Post.repository.PostRepository;
import com.project.travelTracer.global.file.exception.FileException;
import com.project.travelTracer.global.file.service.FileService;
import com.project.travelTracer.global.util.SecurityUtil;
import com.project.travelTracer.member.exception.MemberException;
import com.project.travelTracer.member.exception.MemberExceptionType;
import com.project.travelTracer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;

    @Override
    public void save(PostSaveDto postSaveDto) throws FileException {
        Post post = postSaveDto.toEntity();
        post.confirmWriter(memberRepository.findByUserId(SecurityUtil.getLoginUserId())
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER)));

        postSaveDto.getUploadFile().ifPresent(
                file -> post.updateFilePath(fileService.save(file))
        );
    }

    @Override
    public void update(Long id, PostUpdateDto postUpdateDto) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public PostInfoDto getPostInfo(Long id) {
        return null;
    }

    @Override
    public PostPagingDto getPostList(Pageable pageable, PostSearchCondition postSearchCondition) {
        return null;
    }
}
