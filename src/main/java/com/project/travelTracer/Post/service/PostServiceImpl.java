package com.project.travelTracer.Post.service;

import com.project.travelTracer.Image.Entity.Image;
import com.project.travelTracer.Image.Repository.ImageRepository;
import com.project.travelTracer.Post.condition.PostSearchCondition;
import com.project.travelTracer.Post.dto.PostInfoDto;
import com.project.travelTracer.Post.dto.PostPagingDto;
import com.project.travelTracer.Post.dto.PostSaveDto;
import com.project.travelTracer.Post.dto.PostUpdateDto;
import com.project.travelTracer.Post.entity.Post;
import com.project.travelTracer.Post.exception.PostException;
import com.project.travelTracer.Post.exception.PostExceptionType;
import com.project.travelTracer.Post.repository.PostRepository;
import com.project.travelTracer.global.file.exception.FileException;
import com.project.travelTracer.global.file.handler.FileHandler;
import com.project.travelTracer.global.file.service.FileService;
import com.project.travelTracer.global.util.SecurityUtil;
import com.project.travelTracer.member.exception.MemberException;
import com.project.travelTracer.member.exception.MemberExceptionType;
import com.project.travelTracer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final FileService fileService;
    private final FileHandler fileHandler;

    @Override
    public void save(PostSaveDto postSaveDto) throws Exception {
        Post post = postSaveDto.toEntity();

        post.confirmWriter(memberRepository.findByUserId(SecurityUtil.getLoginUserId())
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER)));

        List<Image> imageList = fileHandler.parseFileInfo(post, postSaveDto.getFiles());

        if(!imageList.isEmpty()) {
            for(Image image : imageList) {
                post.addImage(imageRepository.save(image));
            }
        }
        postRepository.save(post);
    }

    @Override
    public void update(Long id, PostUpdateDto postUpdateDto, List<MultipartFile> files) throws Exception {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new PostException(PostExceptionType.POST_NOT_POUND));

        checkAuthority(post, PostExceptionType.NOT_AUTHORITY_UPDATE_POST);

        postUpdateDto.getTitle().ifPresent(post::updateTitle);
        postUpdateDto.getContent().ifPresent(post::updateContent);
        postUpdateDto.getAddress().ifPresent(post::updateAddress);

        List<Image> imageList = fileHandler.parseFileInfo(post, files);
        if(!imageList.isEmpty()) {
            for(Image image : imageList) {
                imageRepository.save(image);
            }
        }

    }



    private void checkAuthority(Post post, PostExceptionType postExceptionType) {
        if(!post.getWriter().getUserId().equals(SecurityUtil.getLoginUserId())) {
            throw new PostException(postExceptionType);
        }

    }

    @Override
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new PostException(PostExceptionType.POST_NOT_POUND));

        checkAuthority(post,PostExceptionType.NOT_AUTHORITY_DELETE_POST);


        postRepository.delete(post);
    }

    @Override
    public PostInfoDto getPostInfo(Long id, List<Long> fileId) {
        Post post = postRepository.findWithWriterById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_POUND));
        return new PostInfoDto(post, fileId);
    }

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAllDesc();
    }


    @Override
    public PostPagingDto getPostList(Pageable pageable, PostSearchCondition postSearchCondition) {
        return new PostPagingDto(postRepository.search(postSearchCondition, pageable));
    }
}
