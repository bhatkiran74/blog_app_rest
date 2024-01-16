package com.kiran.blog_app_rest.service.impl;

import com.kiran.blog_app_rest.entity.Post;
import com.kiran.blog_app_rest.exception.ResourceNotFoundException;
import com.kiran.blog_app_rest.payload.PostDto;
import com.kiran.blog_app_rest.payload.PostResponse;
import com.kiran.blog_app_rest.repository.PostRepository;
import com.kiran.blog_app_rest.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    /**
     * Adds a new post to the system based on the provided {@link PostDto}.
     * @author Kiransing bhat
     * @param postDto the data transfer object containing information about the new post
     * @return a {@link PostDto} representing the newly added post
     */
    @Override
    public PostDto addPost(PostDto postDto) {
        log.info("PostService executed");
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        log.info("Post saved successfully ", newPost);
        return mapToDto(newPost);
    }

//    without pagination
//    @Override
//    public List<PostDto> findAllPosts() {
//        List<Post> posts = postRepository.findAll();
//        log.info("All posts fetched successfully...");
//        return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
//
//    }

    /**
     * Retrieves a paginated list of posts based on the specified page number and page size.
     * @author Kiransing bhat
     * @param pageNo    the page number to retrieve (0-based index)
     * @param pageSize  the number of posts to include on each page
     * @return a {@link PostResponse} containing the paginated list of posts
     */
    @Override
    public PostResponse findAllPosts(int pageNo, int pageSize,String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

//        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> postList = posts.getContent();
        List<PostDto> content = postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse =new PostResponse();

        postResponse.setContents(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(postResponse.isLast());

        log.info("All posts fetched successfully...");
        return postResponse;

    }

    /**
     * Retrieves a post from the system based on the provided post ID.
     * @author Kiransing bhat
     * @param id the unique identifier of the post to be retrieved
     * @return a {@link PostDto} representing the post with the given ID
     * @throws ResourceNotFoundException if no post is found with the specified ID
     */
    @Override
    public PostDto findPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Id",id));
        log.info("Post found successfully by given id ", post.getId());

        return mapToDto(post);
    }

    /**
     * Updates an existing post in the system based on the provided {@link PostDto} and post ID.
     * @author Kiransing bhat
     * @param postDto the data transfer object containing updated information about the post
     * @param id the unique identifier of the post to be updated
     * @return a {@link PostDto} representing the updated post
     * @throws ResourceNotFoundException if no post is found with the specified ID
     */
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

    /**
     * Deletes a post from the system based on the provided post ID.
     * @author Kiransing bhat
     * @param id the unique identifier of the post to be deleted
     * @throws ResourceNotFoundException if no post is found with the specified ID
     */
    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Id",id));
        log.info("Post deleted successfully ");

        postRepository.delete(post);
    }
    /**
     * Maps a {@link Post} entity to a {@link PostDto} data transfer object.
     * @author Kiransing bhat
     * @param post the post entity to be mapped
     * @return a {@link PostDto} representing the mapped post
     */
    PostDto mapToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .content(post.getContent())
                .build();
    }

    /**
     * Maps a {@link PostDto} data transfer object to a {@link Post} entity.
     * @author Kiransing bhat
     * @param postDto the post data transfer object to be mapped
     * @return a {@link Post} entity representing the mapped post
     */
    private Post mapToEntity(PostDto postDto) {
        return Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .description(postDto.getDescription())
                .build();
    }


}
