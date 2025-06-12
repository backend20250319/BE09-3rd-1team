package org.example.reviewservice.service;

import lombok.RequiredArgsConstructor;
import org.example.reviewservice.dto.ReviewRequest;
import org.example.reviewservice.dto.ReviewResponse;
import org.example.reviewservice.entity.ReviewEntity;
import org.example.reviewservice.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewResponse createReview(ReviewRequest request) {
        ReviewEntity review = ReviewEntity.builder()
                .rate(request.getRate())
                .content(request.getContent())
                .userId(request.getUserId())
                .room(request.getRoom())
                .build();

        ReviewEntity saved = reviewRepository.save(review);
        return toResponse(saved);
    }

    public ReviewResponse getReview(Long id) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다: " + id));
        return toResponse(review);
    }

    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ReviewResponse updateReview(Long id, ReviewRequest request) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다: " + id));

        review.setRate(request.getRate());
        review.setContent(request.getContent());
        review.setRoom(request.getRoom());
        review.setUserId(request.getUserId());

        ReviewEntity updated = reviewRepository.save(review);
        return toResponse(updated);
    }

    public void deleteReview(Long id) {
       if (!reviewRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제할 리뷰를 찾을 수 없습니다: " + id);
        }
        reviewRepository.deleteById(id);
    }

    public List<ReviewResponse> getReviewsByRoom(String room) {
        return reviewRepository.findByRoom(room)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ReviewResponse> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ReviewResponse toResponse(ReviewEntity review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .rate(review.getRate())
                .content(review.getContent())
                .userId(review.getUserId())
                .room(review.getRoom())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}