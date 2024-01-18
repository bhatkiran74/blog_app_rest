package com.kiran.blog_app_rest.service;

import com.kiran.blog_app_rest.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto commentDto);
}
