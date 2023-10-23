package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.dto.ExercisePayload;
import com.ftrainer.ftrainer.entities.Exercise;
import com.ftrainer.ftrainer.repositories.ExerciseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Getter
@Service
public class ExerciseServiceImpl implements ExerciseService{
    private final ExerciseRepository exerciseRepository;
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public Exercise addExercise(ExercisePayload exercisePayload) throws ParseException {
        Exercise exercise = new Exercise();
        exercise.setName(exercisePayload.getName());
        exercise.setDescription(exercisePayload.getDescription());
        return exerciseRepository.save(exercise);
    }


    @Override
    public Exercise findByName(String name) {
        return exerciseRepository.findByName(name);
    }

    @Override
    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    @Override
    public boolean existsByName(String name) {
        return exerciseRepository.existsByName(name);
    }
    @Override
    public boolean deleteExerciseById(Integer id) {
        Optional<Exercise> exercise = exerciseRepository.findById(id);

        if (exercise.isPresent()) {
            exerciseRepository.delete(exercise.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Exercise editExercise(Integer id, ExercisePayload exercisePayload) {
        Optional<Exercise> exerciseOptional = exerciseRepository.findById(id);

        if (exerciseOptional.isPresent()) {
            Exercise exercise = exerciseOptional.get();

            exercise.setName(exercisePayload.getName());
            exercise.setDescription(exercisePayload.getDescription());

            return exerciseRepository.save(exercise);
        } else {
            throw new EntityNotFoundException("Exercise with ID " + id + " not found");
        }

    }

    @Override
    public Exercise findById(Integer id) {
        return exerciseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Exercise> findAllExercisesOrderedByIdAsc() {
        return exerciseRepository.findAllByOrderByIdAsc();
    }
}
