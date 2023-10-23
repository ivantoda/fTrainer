package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.dto.ExercisePayload;
import com.ftrainer.ftrainer.entities.Exercise;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface ExerciseService {
    Exercise addExercise(ExercisePayload exercisePayload) throws ParseException;
    Exercise findByName(String name);

    List<Exercise> findAll();
    boolean existsByName(String name);

    boolean deleteExerciseById(Integer id);

    Exercise editExercise(Integer id, ExercisePayload exercisePayload);

    Exercise findById(Integer id);

    List<Exercise> findAllExercisesOrderedByIdAsc();
}
