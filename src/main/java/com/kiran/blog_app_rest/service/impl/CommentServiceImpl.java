package com.kiran.blog_app_rest.service.impl;

import com.kiran.blog_app_rest.entity.Comment;
import com.kiran.blog_app_rest.entity.Post;
import com.kiran.blog_app_rest.exception.BlogAPIException;
import com.kiran.blog_app_rest.exception.ResourceNotFoundException;
import com.kiran.blog_app_rest.payload.CommentDto;
import com.kiran.blog_app_rest.repository.CommentRepository;
import com.kiran.blog_app_rest.repository.PostRepository;
import com.kiran.blog_app_rest.service.CommentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    /**
     * Creates a new comment for a post based on the provided post ID and CommentDto.
     * @author Kiransingh Bhat
     * @param postId The unique identifier of the post to which the comment is associated
     * @param commentDto The data transfer object containing information about the new comment
     * @return A {@link CommentDto} representing the newly created comment
     * @throws ResourceNotFoundException If no post is found with the specified post ID
     */
    @Override
    public CommentDto createComment(long postId,CommentDto commentDto) {

        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        Comment comment = maptoComment(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        CommentDto savedDto = mapToCommentDto(savedComment);
        return savedDto;
    }

    /**
     * Retrieves comments associated with a post based on the provided post ID.
     * @author Kiransingh Bhat
     * @param postId The unique identifier of the post for which comments are to be retrieved
     * @return A List of {@link CommentDto} representing comments for the specified post
     * @throws ResourceNotFoundException If no comments are found for the given post ID
     */
    @Override
    public List<CommentDto> findCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToCommentDto(comment)).collect(Collectors.toList());
    }

    /**
     * Retrieves a specific comment for a post based on the provided post ID and comment ID.
     * @param postId The unique identifier of the post to which the comment belongs
     * @param commentId The unique identifier of the comment to be retrieved
     * @return A {@link CommentDto} representing the specified comment
     * @throws ResourceNotFoundException If no post or comment is found with the specified IDs
     * @throws BlogAPIException If the retrieved comment does not belong to the specified post
     */
    @Override
    public CommentDto findCommentsByCommentId(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belog to post");
        }
        return mapToCommentDto(comment);
    }

    /**
     * Updates a specific comment for a post based on the provided post ID, comment ID, and CommentDto.
     * @param postId The unique identifier of the post to which the comment belongs
     * @param commentId The unique identifier of the comment to be updated
     * @param commentDto The data transfer object containing updated information for the comment
     * @return A {@link CommentDto} representing the updated comment
     * @throws ResourceNotFoundException If no post or comment is found with the specified IDs
     * @throws BlogAPIException If the updated comment does not belong to the specified post
     */
    @Override
    public CommentDto updateCommentsByCommentId(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belog to post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        log.info("Comment Updated...");
        Comment updatedComment = commentRepository.save(comment);
        return mapToCommentDto(updatedComment);
    }

    /**
     * Maps a Comment entity to a CommentDto.
     * @param comment The Comment entity to be mapped
     * @return A {@link CommentDto} representing the mapped comment
     */
    private CommentDto mapToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .name(comment.getName())
                .email(comment.getEmail())
                .body(comment.getBody())
                .build();
    }

    /**
     * Maps a CommentDto to a Comment entity.
     * @param commentDto The CommentDto to be mapped
     * @return A {@link Comment} entity representing the mapped commentDto
     */
    private Comment maptoComment(CommentDto commentDto) {
        return Comment.builder()
                .name(commentDto.getName())
                .body(commentDto.getBody())
                .email(commentDto.getEmail())
                .build();
    }
}
