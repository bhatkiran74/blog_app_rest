package com.kiran.blog_app_rest.repository.impl;

import com.kiran.blog_app_rest.entity.Post;
import com.kiran.blog_app_rest.repository.PostRepository;
import jakarta.persistence.EntityManager;

public class PostRepositoryImpl extends BaseRepositoryImpl<Post, Long> implements PostRepository {
    public PostRepositoryImpl(EntityManager em) {
        super(Post.class, em);
    }

}
