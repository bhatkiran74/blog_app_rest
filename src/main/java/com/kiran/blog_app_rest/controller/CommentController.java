package com.kiran.blog_app_rest.controller;

import com.kiran.blog_app_rest.payload.CommentDto;
import com.kiran.blog_app_rest.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId, @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}/comment")
    public ResponseEntity<List<CommentDto>> findCommentsByPostId(@PathVariable(value = "postId") long postId){
        return new ResponseEntity<>(commentService.findCommentsByPostId(postId), HttpStatus.OK);
    }

    @GetMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> findCommentsByCommentId(@PathVariable(value = "postId") long postId,@PathVariable(value = "commentId") long commentId){
        return new ResponseEntity<>(commentService.findCommentsByCommentId(postId,commentId), HttpStatus.OK);
    }

    @PutMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> updateCommentsByCommentId(@PathVariable(value = "postId") long postId,@PathVariable(value = "commentId") long commentId, @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateCommentsByCommentId(postId,commentId,commentDto), HttpStatus.OK);
    }
}
