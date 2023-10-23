package com.ftrainer.ftrainer.repositories;

import com.ftrainer.ftrainer.entities.Program;
import com.ftrainer.ftrainer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Integer> {
    Program findByTrainerId(Integer trainerId);

    Program findByClientAndTrainer(User client, User trainer);

    Program findFirstByClientAndTrainerOrderByIdAsc(User client, User trainer);

    Program findByClientAndTrainerOrderByIdDesc(User client, User trainer);

    Program findFirstByClientAndTrainerOrderByIdDesc(User client, User trainer);
}
