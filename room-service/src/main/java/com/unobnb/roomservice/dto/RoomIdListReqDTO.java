package com.unobnb.roomservice.dto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomIdListReqDTO {
    private List<Long> roomIdList;
}
