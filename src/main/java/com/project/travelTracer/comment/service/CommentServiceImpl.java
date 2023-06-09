package com.project.travelTracer.comment.service;

import com.project.travelTracer.Post.exception.PostException;
import com.project.travelTracer.Post.exception.PostExceptionType;
import com.project.travelTracer.Post.repository.PostRepository;
import com.project.travelTracer.comment.dto.CommentSaveDto;
import com.project.travelTracer.comment.dto.CommentUpdateDto;
import com.project.travelTracer.comment.entity.Comment;
import com.project.travelTracer.comment.exception.CommentException;
import com.project.travelTracer.comment.exception.CommentExceptionType;
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
        comment.confirmWriter(memberRepository.findByUserId(SecurityUtil.getLoginUserId())
                .orElseThrow(() ->new MemberException(MemberExceptionType.NOT_FOUND_MEMBER)));
        comment.confirmPost(postRepository.findById(postId)
                .orElseThrow(()-> new PostException(PostExceptionType.POST_NOT_POUND)));
        comment.confirmParent(commentRepository.findById(parentId).orElseThrow(()-> new CommentException(CommentExceptionType.NOT_POUND_COMMENT)));

        commentRepository.save(comment);
    }

    @Override
    public void update(Long id, CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentException(CommentExceptionType.NOT_POUND_COMMENT));
        if(!comment.getWriter().getUserId().equals(SecurityUtil.getLoginUserId())){
            throw new CommentException(CommentExceptionType.NOT_AUTHORITY_UPDATE_COMMENT);
        }
        commentUpdateDto.getContent().ifPresent(comment::updateContent);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment findById(Long id) throws Exception {
        return commentRepository.findById(id).orElseThrow(()-> new CommentException(CommentExceptionType.NOT_POUND_COMMENT));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public void remove(Long id) throws Exception {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new CommentException(CommentExceptionType.NOT_POUND_COMMENT));
        if(!comment.getWriter().getUserId().equals(SecurityUtil.getLoginUserId())){
            throw new CommentException(CommentExceptionType.NOT_AUTHORITY_DELETE_COMMENT);
        }
        comment.remove();
        List<Comment> removableCommentList = comment.findRemovableList();
        commentRepository.deleteAll(removableCommentList);
    }


}
