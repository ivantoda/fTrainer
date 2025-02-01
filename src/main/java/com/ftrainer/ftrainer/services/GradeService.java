package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.Grade;
import com.ftrainer.ftrainer.entities.User;

import java.util.List;

public interface GradeService {

    List<Grade> findAllByTrainer(User trainer);
}
