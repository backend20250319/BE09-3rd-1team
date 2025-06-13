package org.example.reviewservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.reviewservice.common.ApiResponse;
import org.example.reviewservice.dto.ReviewRequest;
import org.example.reviewservice.dto.ReviewResponse;
import org.example.reviewservice.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(@Valid @RequestBody ReviewRequest request) {
        ReviewResponse response = service.createReview(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewResponse>> getReview(@Valid @PathVariable Long id) {
        ReviewResponse response = service.getReview(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getAllReviews() {
        return ResponseEntity.ok(ApiResponse.success(service.getAllReviews()));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
            @PathVariable Long id,
          @Valid  @RequestBody ReviewRequest request
    ) {
        ReviewResponse response = service.updateReview(id, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        service.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/room/{roomName}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByRoom(@PathVariable String roomName) {
        return ResponseEntity.ok(ApiResponse.success(service.getReviewsByRoom(roomName)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(service.getReviewsByUserId(userId)));
    }
}