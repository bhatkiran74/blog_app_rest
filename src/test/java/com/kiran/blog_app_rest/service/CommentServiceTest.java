package com.kiran.blog_app_rest.service;

import com.kiran.blog_app_rest.entity.Comment;
import com.kiran.blog_app_rest.entity.Post;
import com.kiran.blog_app_rest.payload.CommentDto;
import com.kiran.blog_app_rest.payload.PostDto;
import com.kiran.blog_app_rest.repository.CommentRepository;
import com.kiran.blog_app_rest.repository.PostRepository;
import com.kiran.blog_app_rest.service.impl.CommentServiceImpl;
import com.kiran.blog_app_rest.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private CommentServiceImpl commentService;
    @InjectMocks
    private PostServiceImpl postService;
    @BeforeEach
    public void init() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void testAddComment(){

        long postId = 1L;
        CommentDto commentDto = createCommentDto();
        Comment comment = createNewCommentEntity();

        when(commentRepository.save(createNewCommentEntity())).thenReturn(comment);
        when(postRepository.save(createNewPostEntity())).thenReturn(createNewPostEntity());
        // Act
        CommentDto result = commentService.createComment(postId, commentDto);

        // Assert
        assertNotNull(result);
    }

    private CommentDto createCommentDto() {

        return CommentDto.builder()
                .id(1L)
                .name("Kiransing Bhat")
                .email("bhatkiran74@gmail.com")
                .body("Beautiful fort")
                .build();

    }

    private Comment createNewCommentEntity() {

        return Comment.builder()
                .id(1L)
                .name("Kiransing Bhat")
                .email("bhatkiran74@gmail.com")
                .body("Beautiful fort")
                .post(new Post(1L))
                .build();

    }
    private PostDto createPostDto() {
        return PostDto.builder()
                .id(1L)
                .title("abc")
                .description("qwertyuioplkjhgfdsazxcvbnm")
                .content("xyz")
                .build();
    }
    private Post createNewPostEntity() {
        return Post.builder()
                .id(1L)
                .title("abc")
                .description("qwertyuioplkjhgfdsazxcvbnm")
                .content("xyz")
                .build();
    }

    private CommentDto mapToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .name(comment.getName())
                .email(comment.getEmail())
                .body(comment.getBody())
                .build();
    }

    private Comment maptoComment(CommentDto commentDto) {
        return Comment.builder()
                .name(commentDto.getName())
                .body(commentDto.getBody())
                .email(commentDto.getEmail())
                .build();
    }
}
