package com.kiran.blog_app_rest.service;


import com.kiran.blog_app_rest.payload.PostDto;

public interface PostService {
    PostDto addPost(PostDto postDto);
}
