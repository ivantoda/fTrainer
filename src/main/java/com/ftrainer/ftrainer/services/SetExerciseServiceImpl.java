package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.ClientRequest;
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

    private final ClientRequestService clientRequestService;

    @Autowired
    public SetExerciseServiceImpl(UserRepository userRepository, SetExerciseRepository setExerciseRepository, ExerciseService exerciseService, ProgramService programService, ClientRequestService clientRequestService) {
        this.userRepository = userRepository;
        this.setExerciseRepository = setExerciseRepository;
        this.exerciseService = exerciseService;
        this.programService = programService;
        this.clientRequestService = clientRequestService;
    }

    @Override
    public void addSetExercise(List<Integer> exerciseCounts, List<Integer> setCounts, List<Integer> exerciseIds, Integer programId, Integer requestId) {
        Program program = programService.findById(programId).orElse(null);
        if (program == null) {
            throw new IllegalArgumentException("Program with ID " + programId + " not found.");
        }

        int i = 0;
        for(Integer exerciseCount : exerciseCounts){
            SetExercise setExercise = new SetExercise();
            setExercise.setExerciseCount(exerciseCount);
            setExercise.setSetCount(setCounts.get(i));
            setExercise.setExerciseId(exerciseIds.get(i));
            setExercise.setProgram(program);
            setExerciseRepository.save(setExercise);
            i++;
        }
        clientRequestService.setAsInactive(requestId);
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
