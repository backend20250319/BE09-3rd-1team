package org.example.reviewservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private Long id;
    private int rate;
    private String content;
    private Long userId;
    private String room;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}