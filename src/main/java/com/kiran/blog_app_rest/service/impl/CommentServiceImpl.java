package com.kiran.blog_app_rest.service.impl;


import com.kiran.blog_app_rest.entity.Comment;
import com.kiran.blog_app_rest.entity.Post;
import com.kiran.blog_app_rest.exception.ResourceNotFoundException;
import com.kiran.blog_app_rest.payload.CommentDto;
import com.kiran.blog_app_rest.repository.CommentRepository;
import com.kiran.blog_app_rest.repository.PostRepository;
import com.kiran.blog_app_rest.service.CommentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDto createComment(long postId,CommentDto commentDto) {

        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        Comment comment = maptoComment(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        CommentDto savedDto = mapToCommentDto(savedComment);
        return savedDto;
    }

    private CommentDto mapToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .name(comment.getName())
                .email(comment.getEmail())
                .body(comment.getBody())
                .build();
    }

    private Comment maptoComment(CommentDto commentDto) {
        return Comment.builder()
                .name(commentDto.getName())
                .body(commentDto.getBody())
                .email(commentDto.getEmail())
                .build();
    }


}
