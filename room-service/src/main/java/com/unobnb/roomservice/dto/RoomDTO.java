package com.unobnb.roomservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomDTO {
    private Long id;                    // 자동생성
    private String accommodationName;
    private String location;
    private String roomType;
    private int pricePerDay;
    private String sellerId;
}
