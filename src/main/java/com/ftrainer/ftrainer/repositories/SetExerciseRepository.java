package com.ftrainer.ftrainer.repositories;

import com.ftrainer.ftrainer.entities.SetExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetExerciseRepository extends JpaRepository<SetExercise, Integer> {
    List<SetExercise> findByProgramId(Integer programId);
}
