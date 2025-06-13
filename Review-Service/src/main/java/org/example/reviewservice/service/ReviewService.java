package org.example.reviewservice.service;

import lombok.RequiredArgsConstructor;
import org.example.reviewservice.dto.ReviewRequest;
import org.example.reviewservice.dto.ReviewResponse;
import org.example.reviewservice.entity.ReviewEntity;
import org.example.reviewservice.exceptionhandler.GlobalExceptionHandler;
import org.example.reviewservice.repository.ReviewRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewResponse createReview(ReviewRequest request, Long userId) {
        ReviewEntity review = ReviewEntity.builder()
                .rate(request.getRate())
                .content(request.getContent())
                .userId(userId) // 헤더에서 받은 userId 사용
                .room(request.getRoom())
                .build();

        ReviewEntity saved = reviewRepository.save(review);
        return toResponse(saved);
    }

    public ReviewResponse getReview(Long id) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ReviewNotFoundException("리뷰를 찾을 수 없습니다: " + id));
        return toResponse(review);
    }

    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ReviewResponse updateReview(Long id, ReviewRequest request, Long callingUserId) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ReviewNotFoundException("리뷰를 찾을 수 없습니다: " + id));

        // 소유권 확인: 소유자 또는 ADMIN만 업데이트할 수 있습니다.
        // 필요한 경우 '또는 ADMIN' 역할 확인을 여기에 추가할 수 있지만,
        // 현재는 컨트롤러의 @PreAuthorize가 역할을 처리한다고 가정합니다.
        if (!review.getUserId().equals(callingUserId)) {
            throw new GlobalExceptionHandler.UnauthorizedAccessException("리뷰를 수정할 권한이 없습니다.");
        }

        review.setRate(request.getRate());
        review.setContent(request.getContent());
        review.setRoom(request.getRoom());
        // review.setUserId(request.getUserId()); // userId는 업데이트 시 변경되어서는 안 됩니다.

        ReviewEntity updated = reviewRepository.save(review);
        return toResponse(updated);
    }

    public void deleteReview(Long id, Long callingUserId) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ReviewNotFoundException("삭제할 리뷰를 찾을 수 없습니다: " + id));

        // 소유권 확인: 소유자 또는 ADMIN만 삭제할 수 있습니다.
        if (!review.getUserId().equals(callingUserId)) {
            throw new GlobalExceptionHandler.UnauthorizedAccessException("리뷰를 삭제할 권한이 없습니다.");
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