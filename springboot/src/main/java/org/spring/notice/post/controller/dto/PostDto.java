package org.spring.notice.post.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
public class PostDto{

    private String title;

    private String content;

    private UserDto writerDto;

    @Override
    public String toString() {
        return "PostDto{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", writerDto=" + writerDto.getName() +
                '}';
    }
}
