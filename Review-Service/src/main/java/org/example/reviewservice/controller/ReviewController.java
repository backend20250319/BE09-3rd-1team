package org.example.reviewservice.controller;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest request) {
        ReviewResponse response = service.createReview(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Long id) {
        ReviewResponse response = service.getReview(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        return ResponseEntity.ok(service.getAllReviews());
    }


    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable Long id,
            @RequestBody ReviewRequest request
    ) {
        ReviewResponse response = service.updateReview(id, request);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        service.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/room/{roomName}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByRoom(@PathVariable String roomName) {
        return ResponseEntity.ok(service.getReviewsByRoom(roomName));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getReviewsByUserId(userId));
    }
}