package org.spring.notice.post.controller.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto{
    private String uuid;

    private String name;

    private int age;

    private String hobby;
}
