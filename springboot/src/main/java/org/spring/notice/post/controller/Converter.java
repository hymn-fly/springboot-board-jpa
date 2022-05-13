package org.spring.notice.post.controller;

import org.spring.notice.domain.post.Post;
import org.spring.notice.domain.user.User;
import org.spring.notice.post.controller.dto.PostDto;
import org.spring.notice.post.controller.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public UserDto userToUserDto(User user){
        return UserDto.builder()
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .uuid(user.getUuid())
                .build();
    }

    public User userDtoToUser(UserDto userDto){
        return User.create(
                userDto.getUuid(),
                userDto.getName(),
                userDto.getAge(),
                userDto.getHobby());
    }

    public PostDto postToPostDto(Post post){
        return PostDto.builder()
                .content(post.getContent())
                .title(post.getTitle())
                .writerDto(this.userToUserDto(post.getWriter()))
                .build();
    }

    public Post postDtoToPost(PostDto postDto){
        return Post.write(
                postDto.getTitle(),
                postDto.getContent(),
                this.userDtoToUser(postDto.getWriterDto()));
    }
}
