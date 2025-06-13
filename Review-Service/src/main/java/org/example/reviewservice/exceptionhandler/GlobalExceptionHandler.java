package org.example.reviewservice.exceptionhandler; // 패키지 업데이트

import org.example.reviewservice.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 리뷰를 찾을 수 없을 때 발생하는 사용자 정의 예외
    public static class ReviewNotFoundException extends RuntimeException {
        public ReviewNotFoundException(String message) {
            super(message);
        }
    }

    // 권한 없는 접근(예: 다른 사람의 리뷰를 수정하려는 시도)에 대한 사용자 정의 예외
    public static class UnauthorizedAccessException extends RuntimeException {
        public UnauthorizedAccessException(String message) {
            super(message);
        }
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleReviewNotFoundException(ReviewNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure("REVIEW_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiResponse<?>> handleUnauthorizedAccessException(UnauthorizedAccessException ex, WebRequest request) {
        // 게이트웨이가 인증(당신이 누구인지)을 처리했으므로, 이것은 인가(무엇을 할 수 있는지)에 관한 것이므로 403 Forbidden을 사용합니다.
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.failure("UNAUTHORIZED_ACCESS", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure("VALIDATION_ERROR", errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGlobalException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure("INTERNAL_SERVER_ERROR", "서버에 예상치 못한 오류가 발생했습니다: " + ex.getMessage()));
    }
}