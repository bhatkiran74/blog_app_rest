package com.kiran.blog_app_rest.repository.impl;

import com.kiran.blog_app_rest.entity.Comment;
import com.kiran.blog_app_rest.repository.CommentRepository;
import jakarta.persistence.EntityManager;

public class CommentRepositoryImpl extends BaseRepositoryImpl<Comment, Long> implements CommentRepository {
    public CommentRepositoryImpl(EntityManager em) {
        super(Comment.class, em);
    }
}
