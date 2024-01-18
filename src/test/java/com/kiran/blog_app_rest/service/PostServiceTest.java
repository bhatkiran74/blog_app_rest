package com.kiran.blog_app_rest.service;

import com.kiran.blog_app_rest.entity.Post;
import com.kiran.blog_app_rest.exception.ResourceNotFoundException;
import com.kiran.blog_app_rest.payload.PostDto;
import com.kiran.blog_app_rest.payload.PostResponse;
import com.kiran.blog_app_rest.repository.PostRepository;
import com.kiran.blog_app_rest.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;



    @BeforeEach
    public void init() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void testFindPostById(){
        PostDto expectedDto = createPostDto();
        Post expectedPost = createNewPostEntity();
        when(postRepository.findById(expectedDto.getId())).thenReturn(Optional.ofNullable(expectedPost));
        var result = postService.findPostById(1L);
        assertEquals(expectedDto.getId(),result.getId());
    }

    @Test
    void testFindAllPosts(){
        int pageNo = 0;
        int pageSize = 1;
        List<Post> postsData = Arrays.asList(createNewPostEntity());
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> postsPage = new PageImpl<>(postsData, pageable, postsData.size());
        when(postRepository.findAll(pageable)).thenReturn(postsPage);

        PostResponse result = postService.findAllPosts(pageNo, pageSize, "title");

        assertNotNull(result);
        assertEquals(postsData.size(), result.getPageSize());

    }
    @Test
    void testUpdatePost() {

        long postId = 1L;
        PostDto postDto = createPostDto();
        Post existingPost = createNewPostEntity();

        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PostDto result = postService.updatePost(postDto, postId);
        assertNotNull(result);
        assertEquals(postDto.getTitle(), result.getTitle());
    }
    @Test
    void testUpdatePostNotFound() {
        long postId = 2L;
        PostDto postDto =createPostDto();
        when(postRepository.findById(postId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> postService.updatePost(postDto, postId));
        verify(postRepository, times(1)).findById(postId);
    }
    @Test
    void testDeletePostById() {
        long postId = 1L;
        Post existingPost =createNewPostEntity();
        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));

        postService.deletePostById(postId);

        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).delete(existingPost);
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


}
