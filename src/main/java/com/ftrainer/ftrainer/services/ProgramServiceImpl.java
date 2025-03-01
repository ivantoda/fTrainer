package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.dto.UserPayload;
import com.ftrainer.ftrainer.entities.Program;
import com.ftrainer.ftrainer.entities.User;
import com.ftrainer.ftrainer.repositories.ProgramRepository;
import com.ftrainer.ftrainer.repositories.RoleRepository;
import com.ftrainer.ftrainer.repositories.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if(program != null) programRepository.save(program);
        return program;
    }

    @Override
    public Program addProgram(User trainer, User client) {
        Program program = new Program();
        program.setTrainer(trainer);
        program.setClient(client);
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
        Page<Program> programs = programRepository.findByClientId(client.getId(), pageable);

        return programs.stream()
                .map(program -> Map.entry(program, userService.findById2(program.getTrainer().getId())))
                .filter(entry -> searchKeyWord == null || searchKeyWord.isBlank() ||
                        entry.getValue().getFirstname().toLowerCase().contains(searchKeyWord) ||
                        entry.getValue().getLastname().toLowerCase().contains(searchKeyWord))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
