package org.spring.notice.post.service;

import org.spring.notice.domain.post.Post;
import org.spring.notice.domain.post.PostRepository;
import org.spring.notice.domain.user.User;
import org.spring.notice.domain.user.UserRepository;
import org.spring.notice.post.controller.Converter;
import org.spring.notice.post.controller.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final Converter converter;

    public PostService(PostRepository postRepository, UserRepository userRepository, Converter converter) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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
        User writer = userRepository.save(converter.userDtoToUser(postDto.getWriterDto()));

        Post save = postRepository.save(converter.postDtoToPost(postDto).withWriter(writer));

        return converter.postToPostDto(save);
    }

    public PostDto updatePost(Long id, PostDto postDto){
        Post post = converter.postDtoToPost(postDto).withId(id);

        postRepository.save(post);

        return converter.postToPostDto(post);
    }
}
