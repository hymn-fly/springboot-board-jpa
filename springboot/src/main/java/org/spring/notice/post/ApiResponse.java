package org.spring.notice.post;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    private int statusCode;

    private T data;

    public static <T> ApiResponse<T> ok(T data){
        return new ApiResponse<>(200, data);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T data){
        return new ApiResponse<>(statusCode, data);
    }

    private ApiResponse(int statusCode, T data){
        this.statusCode = statusCode;
        this.data = data;
    }
}
