package com.unobnb.roomservice.command.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "방 정보 DTO")
public class RoomDTO {

    @Schema(description = "방 ID", example = "101")
    private Long id;

    @Schema(description = "숙박시설 이름", example = "신라호텔")
    private String accommodationName;

    @Schema(description = "위치", example = "서울 중구 장충동")
    private String location;

    @Schema(description = "방 타입", example = "Suite")
    private String roomType;

    @Schema(description = "일박 가격", example = "150000")
    private int pricePerDay;

    @Schema(description = "판매자 ID", example = "42")
    private Long sellerId;
}
