package com.ftrainer.ftrainer.repositories;

import com.ftrainer.ftrainer.entities.Grade;
import com.ftrainer.ftrainer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer> {

    List<Grade> findByTrainer(User trainer);

}
