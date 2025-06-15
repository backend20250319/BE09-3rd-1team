package com.unobnb.roomservice.command.service;

import com.unobnb.roomservice.command.dto.RoomDTO;
import com.unobnb.roomservice.command.dto.RoomUpdateReqDTO;
import com.unobnb.roomservice.command.entity.Room;
import com.unobnb.roomservice.command.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public RoomDTO save(RoomDTO roomDTO) {
        Room room = modelMapper.map(roomDTO, Room.class);

        if (room.getSellerId() == null) {
            throw new IllegalArgumentException("sellerId는 필수입니다.");
        }
        Room saved = roomRepository.save(room);
        return modelMapper.map(saved, RoomDTO.class);
    }

    public List<RoomDTO> findAll() {
        return roomRepository.findAll().stream()
                .map(room -> modelMapper.map(room, RoomDTO.class))
                .collect(Collectors.toList());
    }

    public RoomDTO findById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 없습니다."));
        return modelMapper.map(room, RoomDTO.class);
    }

    @Transactional
    public RoomUpdateReqDTO updated(RoomUpdateReqDTO roomUpdateReqDTO) {
        Room room = roomRepository.findById(roomUpdateReqDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 없습니다."));

        if (!room.getSellerId().equals(roomUpdateReqDTO.getSellerId())) {
            throw new SecurityException("판매자만 수정할 수 있습니다.");
        }

        room.setAccommodationName(roomUpdateReqDTO.getAccommodationName());
        room.setLocation(roomUpdateReqDTO.getLocation());
        room.setRoomType(roomUpdateReqDTO.getRoomType());
        room.setPricePerDay(roomUpdateReqDTO.getPricePerDay());

        Room updated = roomRepository.save(room);
        return modelMapper.map(updated, RoomUpdateReqDTO.class);
    }

    public List<RoomDTO> findRoomsByIds(List<Long> ids) {
        return roomRepository.findAllById(ids).stream()
                .map(room -> modelMapper.map(room, RoomDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    @Transactional
    public void deleteRoomWithAuth(Long roomId, Long sellerId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 방이 없습니다."));

        if (!room.getSellerId().equals(sellerId)) {
            throw new SecurityException("판매자만 삭제할 수 있습니다.");
        }
        roomRepository.deleteById(roomId);
    }


}
