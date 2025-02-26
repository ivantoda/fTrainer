package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.Exercise;
import com.ftrainer.ftrainer.entities.SetExercise;

import java.util.List;
import java.util.Map;


public interface SetExerciseService {
    SetExercise addSetExercise(Integer exerciseCount, Integer setCount, Integer exerciseId,Integer programId);
    Map<SetExercise, Exercise> getByProgramId(Integer programId);
}
