package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.User;

import java.util.List;

public interface ClientService {
    List<User> getTrainers();

    List<User> getTrainersAsc();

    List<User> getTrainersDesc();

}
