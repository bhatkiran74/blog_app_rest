package com.kiran.blog_app_rest.service;

import com.kiran.blog_app_rest.payload.CommentDto;
import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto commentDto);
    List<CommentDto> findCommentsByPostId(long postId);
    CommentDto findCommentsByCommentId(long postId, long commentId);
    CommentDto updateCommentsByCommentId(long postId, long commentId, CommentDto commentDto);
    void deleteCommentsByCommentId(long postId, long commentId);
}
