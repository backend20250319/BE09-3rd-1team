package com.unobnb.roomservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomUpdateReqDTO {
    private Long id;                    // 자동생성
    private String accommodationName;   // 숙박시설 이름
    private String location;            // 위치
    private String roomType;            // 방 타입
    private int pricePerDay;            // 일박 가격
}
