package com.kiran.blog_app_rest.service;


import com.kiran.blog_app_rest.payload.PostDto;
import com.kiran.blog_app_rest.payload.PostResponse;

public interface PostService {
    PostDto addPost(PostDto postDto);
    PostResponse findAllPosts(int pageNo, int pageSize, String sortBy,String sortDir);
    PostDto findPostById(long id);
    PostDto updatePost(PostDto postDto, long id);
    void deletePostById(long id);

}
