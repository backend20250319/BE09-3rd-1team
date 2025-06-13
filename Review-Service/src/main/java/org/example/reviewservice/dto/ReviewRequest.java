package org.example.reviewservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequest {
    private int rate;
    private String content;
    private Long userId;
    private String room;
}