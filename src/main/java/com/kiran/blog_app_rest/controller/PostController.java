package com.kiran.blog_app_rest.controller;

import com.kiran.blog_app_rest.payload.PostDto;
import com.kiran.blog_app_rest.payload.PostResponse;
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

//    without pagination and sort
//    @GetMapping
//    public ResponseEntity<List<PostDto>> findPosts(){
//        return new ResponseEntity<>(postService.findAllPosts(),HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<PostResponse> findPosts(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "2",required = false) int pageSize
    ){
        return new ResponseEntity<>(postService.findAllPosts(pageNo,pageSize),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findPostById(@PathVariable("id") long id){
        return new ResponseEntity<>(postService.findPostById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("id") long id){
        return new ResponseEntity<>(postService.updatePost(postDto,id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post deleted successfully...",HttpStatus.OK);
    }


}
