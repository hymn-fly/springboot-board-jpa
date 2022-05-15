package org.spring.notice.post.controller;

import org.spring.notice.post.ApiResponse;
import org.spring.notice.post.controller.dto.PostDto;
import org.spring.notice.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ApiResponse<String> noSuchElementException(NoSuchElementException e){
        return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @GetMapping("/posts")
    public ApiResponse<List<PostDto>> getPosts(Pageable pageable){
        Page<PostDto> posts = postService.getPosts(pageable);
        return ApiResponse.ok(posts.getContent());
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> getPost(@PathVariable Long id){
        return ApiResponse.ok(postService.getPost(id));
    }

    @PostMapping("/posts")
    public ApiResponse<PostDto> writePost(@RequestBody PostDto postDto){
        return ApiResponse.ok(postService.writePost(postDto));
    }

    @PostMapping("/posts/{id}")
    public ApiResponse<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto postDto){
        return ApiResponse.ok(postService.updatePost(id, postDto));
    }
}
