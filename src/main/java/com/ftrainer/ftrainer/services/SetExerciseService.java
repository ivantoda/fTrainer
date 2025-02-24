package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.Exercise;
import com.ftrainer.ftrainer.entities.SetExercise;

import java.util.List;
import java.util.Map;


public interface SetExerciseService {
    SetExercise addSetExercise(SetExercise setExercise);
    Map<SetExercise, Exercise> getByProgramId(Integer programId);
}
