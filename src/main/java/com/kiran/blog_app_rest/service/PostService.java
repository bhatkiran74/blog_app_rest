package com.kiran.blog_app_rest.service;


import com.kiran.blog_app_rest.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto addPost(PostDto postDto);

    List<PostDto> findAllPosts();
}
