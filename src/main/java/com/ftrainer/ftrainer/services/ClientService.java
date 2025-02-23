package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientService {
    List<User> getTrainers();

    List<User> getTrainersAsc();

    List<User> getTrainersDesc();

    Page<User> findAllTrainersSortedByFirstnameDesc(String searchKeyWord, int pageNo, int pageSize);

    Page<User> findAllTrainersSortedByFirstnameAsc(String searchKeyWord, int pageNo, int pageSize);


}
