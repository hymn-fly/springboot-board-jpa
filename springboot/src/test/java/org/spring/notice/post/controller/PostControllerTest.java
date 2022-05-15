package org.spring.notice.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.spring.notice.domain.post.PostRepository;
import org.spring.notice.domain.user.UserRepository;
import org.spring.notice.post.controller.dto.PostDto;
import org.spring.notice.post.controller.dto.UserDto;
import org.spring.notice.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.spring.notice.fixture.Fixture.getUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostControllerTest {

    private final UserDto userDto = UserDto.builder()
            .name(getUser().getName())
            .age(getUser().getAge())
            .hobby(getUser().getHobby())
            .uuid(getUser().getUuid())
            .build();

    private final PostDto postDto = PostDto.builder()
            .title("새로운 포스팅")
            .content("좋은밤 좋은꿈 안녕")
            .writerDto(userDto)
            .build();

    private final PostDto postDto2 = PostDto.builder()
            .title("자본주의")
            .content("어디서 와서 어디로 가는가")
            .writerDto(userDto)
            .build();

    private final PostDto newPost = PostDto.builder()
            .title("오만과 편견")
            .content("제인 오스틴")
            .writerDto(userDto)
            .build();

    @Autowired
    MockMvc mvc;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    void setup(){
        postService.writePost(postDto);
        postService.writePost(postDto2);
    }
    @AfterAll
    void tearDown(){
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("[API][GET] /posts")
    void getPostsTest() throws Exception {
        mvc.perform(get("/posts")
                        .param("page", "0")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value(postDto.getTitle()))
                .andExpect(jsonPath("$.data[1].title").value(postDto2.getTitle()))
                .andDo(print());
    }

    @Test
    @Order(2)
    @DisplayName("[API][GET] /posts/{id}")
    void getPostTest() throws Exception {
        mvc.perform(get("/posts/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(postDto.getTitle()))
                .andDo(print());
    }

    @Test
    @Order(3)
    @DisplayName("[API][POST] /posts")
    void writePostTest() throws Exception {
        mvc.perform(post("/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(newPost.getTitle()))
                .andDo(print());

        postRepository.deleteById(3L);
    }

    @Test
    @Order(4)
    @DisplayName("[API][POST] /posts/{id}")
    void updatePostTest() throws Exception {
        mvc.perform(post("/posts/{id}", 1L)
                        .content(objectMapper.writeValueAsString(newPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(newPost.getTitle()))
                .andDo(print());
    }

    @Test
    @DisplayName("[API][GET] /posts/{id} error")
    void getPostErrorTest() throws Exception {
        mvc.perform(get("/posts/{id}", 10L))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()))
                .andDo(print());
    }
}