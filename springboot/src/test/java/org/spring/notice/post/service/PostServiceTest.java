package org.spring.notice.post.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spring.notice.domain.post.Post;
import org.spring.notice.domain.post.PostRepository;
import org.spring.notice.domain.user.User;
import org.spring.notice.domain.user.UserRepository;
import org.spring.notice.post.controller.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.spring.notice.fixture.Fixture.getUser;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setup(){
        User savedUser = userRepository.save(getUser());

        Post post1 = Post.write("테스트1", "테스트 내용", savedUser);
        Post post2 = Post.write("테스트2", "테스트 내용2", savedUser);
        Post post3 = Post.write("테스트3", "테스트 내용3", savedUser);
        Post post4 = Post.write("테스트1", "블라블라블라", savedUser);
        Post post5 = Post.write("테스트2", "오오홍", savedUser);

        postRepository.saveAll(List.of(post1, post2, post3, post4, post5));
    }

    @Test
    void 페이징조회_테스트() {
        Page<PostDto> posts = postService.getPosts(PageRequest.of(0, 3));
        System.out.println(posts.getContent());
    }
}