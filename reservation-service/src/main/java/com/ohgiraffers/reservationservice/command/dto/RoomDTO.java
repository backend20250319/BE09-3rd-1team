package com.ohgiraffers.reservationservice.command.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
public class RoomDTO {
    @Schema(description = "숙소 ID", example = "7")
    private Long id;

    @Schema(description = "숙소명", example = "신라스테이 삼성 (코엑스센터)")
    private String accommodationName;

    @Schema(description = "위치", example = "서울특별시 강남구 영동대로 506")
    private String location;

    @Schema(description = "객실 타입", example = "디럭스 더블")
    private String roomType;

    @Schema(description = "일박 요금", example = "300000")
    private int pricePerDay;

    @Schema(description = "판매자 ID", example = "343")
    private Long sellerId;
}