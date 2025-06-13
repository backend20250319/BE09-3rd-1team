package org.example.reviewservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.reviewservice.common.ApiResponse;
import org.example.reviewservice.dto.ReviewRequest;
import org.example.reviewservice.dto.ReviewResponse;
import org.example.reviewservice.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // @PreAuthorize를 위해 임포트
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')") // USER 또는 ADMIN만 생성 가능
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
            @Valid @RequestBody ReviewRequest request,
            @RequestHeader("X-User-Id") Long userId
    ) {
        ReviewResponse response = service.createReview(request, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()") // 누구나 특정 리뷰를 읽을 수 있음
    public ResponseEntity<ApiResponse<ReviewResponse>> getReview(@PathVariable Long id) {
        ReviewResponse response = service.getReview(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    @PreAuthorize("permitAll()") // 누구나 모든 리뷰를 읽을 수 있음
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getAllReviews() {
        return ResponseEntity.ok(ApiResponse.success(service.getAllReviews()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')") // USER 또는 ADMIN만 업데이트 가능
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRequest request,
            @RequestHeader("X-User-Id") Long userId
    ) {
        ReviewResponse response = service.updateReview(id, request, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')") // USER 또는 ADMIN만 삭제 가능
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) {
        service.deleteReview(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/room/{roomName}")
    @PreAuthorize("permitAll()") // 누구나 숙소별 리뷰 검색 가능
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByRoom(@PathVariable String roomName) {
        return ResponseEntity.ok(ApiResponse.success(service.getReviewsByRoom(roomName)));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("permitAll()") // 누구나 사용자 ID별 리뷰 검색 가능
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(service.getReviewsByUserId(userId)));
    }
}