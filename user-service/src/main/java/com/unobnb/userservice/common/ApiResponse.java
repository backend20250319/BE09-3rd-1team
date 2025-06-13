package com.unobnb.userservice.common;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private String errorCode;
    private String message;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
            .success(true)
            .data(data)
            .timestamp(LocalDateTime.now())
            .build();
    }

    public static <T> ApiResponse<T> failure(String errorCode, String errorMessage) {
        return ApiResponse.<T>builder()
            .success(false)
            .errorCode(errorCode)
            .message(errorMessage)
            .timestamp(LocalDateTime.now())
            .build();
    }
}
