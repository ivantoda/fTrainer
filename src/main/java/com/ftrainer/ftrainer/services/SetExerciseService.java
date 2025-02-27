package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.Exercise;
import com.ftrainer.ftrainer.entities.SetExercise;

import java.util.List;
import java.util.Map;


public interface SetExerciseService {
    void addSetExercise(List<Integer> exerciseCount, List<Integer> setCount, List<Integer> exerciseId, Integer programId, Integer requestId);
    Map<SetExercise, Exercise> getByProgramId(Integer programId);
}
