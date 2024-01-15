package com.kiran.blog_app_rest.service.impl;

import com.kiran.blog_app_rest.entity.Post;
import com.kiran.blog_app_rest.payload.PostDto;
import com.kiran.blog_app_rest.repository.PostRepository;
import com.kiran.blog_app_rest.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDto addPost(PostDto postDto) {
        log.info("PostService executed");
        Post post=Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .description(postDto.getDescription())
                .build();

        Post newPost =postRepository.save(post);
        log.info("Post saved successfully ",newPost);

        return mapToDto(newPost);
    }

    PostDto mapToDto(Post post){
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .content(post.getContent())
                .build();
    }
}
