package org.example.reviewservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.reviewservice.common.ApiResponse;
import org.example.reviewservice.dto.ReviewRequest;
import org.example.reviewservice.dto.ReviewResponse;
import org.example.reviewservice.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Review API")
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @Operation(summary = "리뷰 생성", description = "인가(CUSTOMER)가 있어야 생성가능")
    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
        @Valid @RequestBody ReviewRequest request,
        @AuthenticationPrincipal String userId
    ) {
        ReviewResponse response = service.createReview(request, Long.valueOf(userId));
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "리뷰조회", description = "reivew id로 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewResponse>> getReview(@PathVariable Long id) {
        ReviewResponse response = service.getReview(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "모든 리뷰 조회", description = "모든 리뷰를 조회한다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getAllReviews() {
        return ResponseEntity.ok(ApiResponse.success(service.getAllReviews()));
    }

    @Operation(summary = "리뷰 수정", description = "인가(CUSTOMER)가 있어야 수정 가능")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
        @PathVariable Long id,
        @Valid @RequestBody ReviewRequest request,
        @AuthenticationPrincipal String userId
    ) {
        ReviewResponse response = service.updateReview(id, request, Long.valueOf(userId));
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "리뷰 삭제", description = "인가(CUSTOMER)가 있어야 삭제 가능")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<Void> deleteReview(
        @PathVariable Long id,
        @AuthenticationPrincipal String userId

    ) {
        service.deleteReview(id, Long.valueOf(userId));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "리뷰 조회", description = "방 이름으로 조회")
    @GetMapping("/room/{roomName}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByRoom(
        @PathVariable String roomName) {
        return ResponseEntity.ok(ApiResponse.success(service.getReviewsByRoom(roomName)));
    }

    @Operation(summary = "리뷰 조회", description = "userId로 조회")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByUserId(
        @PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(service.getReviewsByUserId(userId)));
    }
}