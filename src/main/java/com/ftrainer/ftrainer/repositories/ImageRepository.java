package com.ftrainer.ftrainer.repositories;

import com.ftrainer.ftrainer.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Transactional(readOnly = true)
    Optional<Image> findByUserId(int userId);
}
