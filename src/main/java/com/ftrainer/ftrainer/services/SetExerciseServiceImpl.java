package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.Exercise;
import com.ftrainer.ftrainer.entities.Program;
import com.ftrainer.ftrainer.entities.SetExercise;
import com.ftrainer.ftrainer.repositories.SetExerciseRepository;
import com.ftrainer.ftrainer.repositories.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Service
public class SetExerciseServiceImpl implements SetExerciseService{

    private final UserRepository userRepository;

    private final SetExerciseRepository setExerciseRepository;

    private final ExerciseService exerciseService;

    private final ProgramService programService;

    @Autowired
    public SetExerciseServiceImpl(UserRepository userRepository, SetExerciseRepository setExerciseRepository, ExerciseService exerciseService, ProgramService programService) {
        this.userRepository = userRepository;
        this.setExerciseRepository = setExerciseRepository;
        this.exerciseService = exerciseService;
        this.programService = programService;
    }

    @Override
    public SetExercise addSetExercise(Integer exerciseCount, Integer setCount, Integer exerciseId,Integer programId) {
        SetExercise setExercise = new SetExercise();
        setExercise.setExerciseCount(exerciseCount);
        setExercise.setSetCount(setCount);
        setExercise.setExerciseId(exerciseId);
        Program program = programService.findById(programId).orElse(null);
        setExercise.setProgram(program);
        setExerciseRepository.save(setExercise);
        return setExercise;
    }

    public Map<SetExercise, Exercise> getByProgramId(Integer programId) {
        Map<SetExercise, Exercise> setExerciseExerciseMap = new HashMap<>();
        List<SetExercise> setExercises = setExerciseRepository.findByProgramId(programId);
        for (SetExercise setExercise : setExercises) {
            Exercise exercise = exerciseService.findById(setExercise.getExerciseId());
            setExerciseExerciseMap.put(setExercise, exercise);
        }
        return setExerciseExerciseMap;
    }
}
