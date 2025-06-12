package org.example.reviewservice.repository;

import org.example.reviewservice.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByRoom(String room);
    List<ReviewEntity> findByUserId(Long userId);
}