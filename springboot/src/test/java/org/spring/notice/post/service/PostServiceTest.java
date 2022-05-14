package org.spring.notice.post.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spring.notice.domain.post.Post;
import org.spring.notice.domain.post.PostRepository;
import org.spring.notice.domain.user.User;
import org.spring.notice.domain.user.UserRepository;
import org.spring.notice.post.controller.dto.PostDto;
import org.spring.notice.post.controller.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.spring.notice.fixture.Fixture.getUser;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    private Post post1;
    private Post post2;
    private Post post3;
    private Post post4;
    private Post post5;

    @BeforeEach
    void setup(){
        User savedUser = userRepository.save(getUser());

        post1 = Post.write("테스트1", "테스트 내용", savedUser);
        post2 = Post.write("테스트2", "테스트 내용2", savedUser);
        post3 = Post.write("테스트3", "테스트 내용3", savedUser);
        post4 = Post.write("테스트1", "블라블라블라", savedUser);
        post5 = Post.write("테스트2", "오오홍", savedUser);

        postRepository.saveAll(List.of(post1, post2, post3, post4, post5));
    }


    @Test
    void 페이징조회_테스트() {
        //Given
        // When
        List<PostDto> posts = postService.getPosts(PageRequest.of(0, 3)).getContent();

        // Then
        assertThat(posts.get(0).getTitle()).isEqualTo("테스트1");
        assertThat(posts.get(0).getContent()).isEqualTo("테스트 내용");

        assertThat(posts.get(2).getTitle()).isEqualTo("테스트3");
        assertThat(posts.get(2).getContent()).isEqualTo("테스트 내용3");
    }

    @Test
    void id로_포스팅조회_테스트() {
        //Given
        // When
        PostDto post = postService.getPost(post1.getId());

        // Then
        assertThat(post.getTitle()).isEqualTo("테스트1");
        assertThat(post.getContent()).isEqualTo("테스트 내용");
        assertThat(post.getWriterDto().getUuid()).isEqualTo(getUser().getUuid());
    }

    @Test
    void 포스팅작성_테스트() {
        //Given
        UserDto userDto = UserDto.builder()
                .name(getUser().getName())
                .age(getUser().getAge())
                .hobby(getUser().getHobby())
                .uuid(getUser().getUuid())
                .build();

        PostDto postDto = PostDto.builder()
                .title("새로운 포스팅")
                .content("좋은밤 좋은꿈 안녕")
                .writerDto(userDto)
                .build();

        // When
        PostDto savedDto = postService.writePost(postDto);

        // Then
        assertThat(savedDto).usingRecursiveComparison().isEqualTo(postDto);
    }

    @Test
    void 포스팅_업데이트_테스트() {
        //Given
        UserDto userDto = UserDto.builder()
                .name(getUser().getName())
                .age(getUser().getAge())
                .hobby(getUser().getHobby())
                .uuid(getUser().getUuid())
                .build();

        PostDto postDto = PostDto.builder()
                .title("새로운 포스팅")
                .content("좋은밤 좋은꿈 안녕")
                .writerDto(userDto)
                .build();
        // When
        postService.updatePost(1L, postDto);
        PostDto post = postService.getPost(1L);

        // Then
        assertThat(post).usingRecursiveComparison().isEqualTo(postDto);
    }
}