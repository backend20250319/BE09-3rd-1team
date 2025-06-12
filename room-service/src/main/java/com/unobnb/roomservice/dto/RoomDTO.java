package com.unobnb.roomservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomDTO {
    private Long id;
    private String accommodationName;
    private String location;
    private String roomType;
    private int pricePerDay;
    private String sellerId;
}
