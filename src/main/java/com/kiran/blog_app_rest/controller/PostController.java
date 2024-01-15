package com.kiran.blog_app_rest.controller;

import com.kiran.blog_app_rest.payload.PostDto;
import com.kiran.blog_app_rest.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@Log4j2
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto){
        log.info("postcontroller executed");
        PostDto resPostDto = postService.addPost(postDto);
        return new ResponseEntity<>(resPostDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> findPosts(){
        return new ResponseEntity<>(postService.findAllPosts(),HttpStatus.OK);
    }


}
