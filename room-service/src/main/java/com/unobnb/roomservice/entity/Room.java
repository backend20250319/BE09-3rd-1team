package com.unobnb.roomservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_room")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accommodation_name")
    private String accommodationName;

    @Column(name = "location")
    private String location;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "price_per_day")
    private int pricePerDay;

    @Column(name = "seller_id")
    private String sellerId;

    public void changePrice(int newPrice) {
        this.pricePerDay = newPrice;
    }
}

