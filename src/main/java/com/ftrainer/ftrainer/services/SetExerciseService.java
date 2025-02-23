package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.SetExercise;

import java.util.List;


public interface SetExerciseService {
    public SetExercise addSetExercise(SetExercise setExercise);
    public List<SetExercise> getByProgramId(Integer programId);
}
