package com.unobnb.roomservice.service;

import com.unobnb.roomservice.dto.RoomDTO;
import com.unobnb.roomservice.entity.Room;
import com.unobnb.roomservice.repository.RoomRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
        return modelMapper.map(room, RoomDTO.class);
    }

    @Transactional
    public RoomDTO update(RoomDTO roomDTO) {
        Room room = modelMapper.map(roomDTO, Room.class);
        Room updated = roomRepository.save(room);
        return modelMapper.map(updated, RoomDTO.class);
    }

    @Transactional
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }
}
