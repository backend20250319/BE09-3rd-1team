package com.unobnb.roomservice.command.repository;

import com.unobnb.roomservice.command.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {


}

