package com.kiran.blog_app_rest.repository.impl;

import com.kiran.blog_app_rest.entity.Comment;
import com.kiran.blog_app_rest.repository.CommentRepository;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CommentRepositoryImpl extends BaseRepositoryImpl<Comment, Long> implements CommentRepository {
    public CommentRepositoryImpl(EntityManager em) {
        super(Comment.class, em);
    }

    @Override
    public List<Comment> findByPostId(long postId) {
        return jpaQueryFactory
                .select(comment)
                .from(comment)
                .where(comment.post.id.eq(postId))
                .fetch();
    }
}
