package org.spring.notice.post.service;

import org.spring.notice.domain.post.Post;
import org.spring.notice.domain.post.PostRepository;
import org.spring.notice.post.controller.Converter;
import org.spring.notice.post.controller.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final Converter converter;

    public PostService(PostRepository postRepository, Converter converter) {
        this.postRepository = postRepository;
        this.converter = converter;
    }

    public Page<PostDto> getPosts(Pageable pageable){
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(converter::postToPostDto);
    }

    public PostDto getPost(Long id){
        Optional<Post> post = postRepository.findById(id);

        return post.map(converter::postToPostDto).orElseThrow();
    }

    public PostDto writePost(PostDto postDto){
        Post save = postRepository.save(converter.postDtoToPost(postDto));

        return converter.postToPostDto(save);
    }

    public PostDto updatePost(Long id, PostDto postDto){
        Post post = converter.postDtoToPost(postDto).withId(id);

        postRepository.save(post);

        return converter.postToPostDto(post);
    }
}
