package com.kiran.blog_app_rest.service.impl;

import com.kiran.blog_app_rest.entity.Post;
import com.kiran.blog_app_rest.exception.ResourceNotFoundException;
import com.kiran.blog_app_rest.payload.PostDto;
import com.kiran.blog_app_rest.repository.PostRepository;
import com.kiran.blog_app_rest.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDto addPost(PostDto postDto) {
        log.info("PostService executed");
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        log.info("Post saved successfully ", newPost);
        return mapToDto(newPost);
    }


    @Override
    public List<PostDto> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        log.info("All posts fetched successfully...");
        return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

    }

    @Override
    public PostDto findPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Id",id));
        log.info("Post found successfully by given id ", post.getId());

        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {

        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Id",id));

        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        Post updatedPost = postRepository.save(post);
        log.info("Post updated successfully ");

        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Id",id));
        log.info("Post deleted successfully ");

        postRepository.delete(post);
    }

    PostDto mapToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .content(post.getContent())
                .build();
    }


    private Post mapToEntity(PostDto postDto) {
        return Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .description(postDto.getDescription())
                .build();
    }


}
