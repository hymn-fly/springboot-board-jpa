package org.spring.notice.post.controller;

import org.spring.notice.post.ApiResponse;
import org.spring.notice.post.controller.dto.PostDto;
import org.spring.notice.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
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
    public ApiResponse<PostDto> writePost(PostDto postDto){
        return ApiResponse.ok(postService.writePost(postDto));
    }

    @PostMapping("/posts/{id}")
    public ApiResponse<PostDto> updatePost(@PathVariable Long id, PostDto postDto){
        return ApiResponse.ok(postService.updatePost(id, postDto));
    }
}
