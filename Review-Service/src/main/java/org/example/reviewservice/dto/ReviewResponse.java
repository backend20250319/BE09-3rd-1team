package org.example.reviewservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "리뷰 응답 DTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {

    @Schema(description = "리뷰 ID", example = "1")
    private Long id;

    @Schema(description = "평점", example = "4")
    private int rate;

    @Schema(description = "평점", example = "방이 정말 좋아요!")
    private String content;

    @Schema(description = "유저 ID", example = "1")
    private Long userId;

    @Schema(description = "방이름", example = "신라호텔")
    private String room;

    @Schema(description = "생성 시간", example = "2020.01.01 01:01:01")
    private LocalDateTime createdAt;

    @Schema(description = "수정 시간", example = "2020.01.01 01:01:01")
    private LocalDateTime updatedAt;
}