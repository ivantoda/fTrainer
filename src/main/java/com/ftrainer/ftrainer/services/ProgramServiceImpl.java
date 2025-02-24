package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.dto.UserPayload;
import com.ftrainer.ftrainer.entities.Program;
import com.ftrainer.ftrainer.repositories.ProgramRepository;
import com.ftrainer.ftrainer.repositories.RoleRepository;
import com.ftrainer.ftrainer.repositories.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Service
public class ProgramServiceImpl implements ProgramService{
    private final UserRepository userRepository;

    private final UserService userService;

    private final RoleRepository roleRepository;
    private final ProgramRepository programRepository;

    @Autowired
    public ProgramServiceImpl(UserRepository userRepository, UserService userService, RoleRepository roleRepository,
                              ProgramRepository programRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.programRepository = programRepository;
    }

    @Override
    public Program addProgram(Program program) {
        programRepository.save(program);
        return program;
    }
    @Override
    public Optional<Program> findById(Integer programId) {
        return programRepository.findById(programId);
    }

    @Override
    public void delete(Program program) {
        programRepository.delete(program);
    }


    @Override
    public Map<Program, UserPayload> findProgramsByClient(UserPayload client, String searchKeyWord, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id").ascending());
        List<Program> programs = programRepository.findByClientId(client.getId());
        Map<Program, UserPayload> programTrainerMap = new HashMap<>();

        for (Program program: programs) {
            int trainerId = program.getTrainer().getId();
            UserPayload trainer = userService.findById2(trainerId);
            if(searchKeyWord == null || searchKeyWord.isBlank() ||
                    trainer.getFirstname().toLowerCase().contains(searchKeyWord) ||
                    trainer.getLastname().toLowerCase().contains(searchKeyWord)){
                programTrainerMap.put(program,trainer);
            }
        }
        return programTrainerMap;
    }
}
