package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.Program;
import com.ftrainer.ftrainer.repositories.ProgramRepository;
import com.ftrainer.ftrainer.repositories.RoleRepository;
import com.ftrainer.ftrainer.repositories.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Service
public class ProgramServiceImpl implements ProgramService{
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final ProgramRepository programRepository;

    @Autowired
    public ProgramServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                              ProgramRepository programRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.programRepository = programRepository;
    }

    @Override
    public Program addProgram(Program program) {
        programRepository.save(program);
        return program;
    }
}
