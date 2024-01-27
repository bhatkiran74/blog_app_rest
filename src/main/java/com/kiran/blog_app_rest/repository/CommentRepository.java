package com.kiran.blog_app_rest.repository;

import com.kiran.blog_app_rest.entity.Comment;
import java.util.List;

public interface CommentRepository extends BaseRepository<Comment,Long>{
    List<Comment> findByPostId(long postId);
}
