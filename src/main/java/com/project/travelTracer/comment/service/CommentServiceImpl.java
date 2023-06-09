package com.project.travelTracer.comment.service;

import com.project.travelTracer.Post.exception.PostException;
import com.project.travelTracer.Post.exception.PostExceptionType;
import com.project.travelTracer.Post.repository.PostRepository;
import com.project.travelTracer.comment.dto.CommentSaveDto;
import com.project.travelTracer.comment.dto.CommentUpdateDto;
import com.project.travelTracer.comment.entity.Comment;
import com.project.travelTracer.comment.repository.CommentRepository;
import com.project.travelTracer.global.util.SecurityUtil;
import com.project.travelTracer.member.exception.MemberException;
import com.project.travelTracer.member.exception.MemberExceptionType;
import com.project.travelTracer.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements  CommentService{

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    @Override
    public void save(Long postId, CommentSaveDto commentSaveDto) {
        Comment comment = commentSaveDto.toEntity();
        comment.confirmWriter(memberRepository.findByUserId(SecurityUtil.getLoginUserId())
                .orElseThrow(() ->new MemberException(MemberExceptionType.NOT_FOUND_MEMBER)));
        comment.confirmPost(postRepository.findById(postId)
                .orElseThrow(()-> new PostException(PostExceptionType.POST_NOT_POUND)));
        commentRepository.save(comment);
    }

    @Override
    public void saveRecomment(Long postId, Long parentId, CommentSaveDto commentSaveDto) {
        Comment comment = commentSaveDto.toEntity();


    }

    @Override
    public void update(Long id, CommentUpdateDto commentUpdateDto) {

    }

    @Override
    @Transactional(readOnly = true)
    public Comment findById(Long id) throws Exception {
        return commentRepository.findById(id).orElseThrow(()-> new Exception("댓글이 없습니다"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public void remove(Long id) throws Exception {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new Exception("댓글이 없습니다"));
        comment.remove();
        List<Comment> removableCommentList = comment.findRemovableList();
        removableCommentList.forEach(removableComment -> commentRepository.delete(removableComment));
    }


}
