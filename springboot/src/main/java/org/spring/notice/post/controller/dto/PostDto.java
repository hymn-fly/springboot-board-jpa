package org.spring.notice.post.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDto{

    private String title;

    private String content;

    private UserDto writerDto;

}
