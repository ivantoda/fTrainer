package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.dto.UserPayload;
import com.ftrainer.ftrainer.entities.Program;
import java.util.Map;
import java.util.Optional;

public interface ProgramService {
    Program addProgram(Program program);

    Optional<Program> findById(Integer programId);

    void delete(Program program);

    Map<Program, UserPayload> findProgramsByClient(UserPayload client, String searchKeyWord, Integer pageNo, Integer pageSize );
}
