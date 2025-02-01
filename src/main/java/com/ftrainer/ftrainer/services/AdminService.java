package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.User;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminService {
    List<User> getClients();
    List<User> getTrainers();

    public User getUserById(Integer id) throws NotFoundException;

    void deleteUserById(Integer id);

    Page<User> findAllClientsSortedByFirstnameDesc(String searchKeyWord, int pageNo, int pageSize);

    Page<User> findAllClientsSortedByFirstnameAsc(String searchKeyWord, int pageNo, int pageSize);

    Page<User> findAllTrainersSortedByFirstnameDesc(String searchKeyWord, int pageNo, int pageSize);

    Page<User> findAllTrainersSortedByFirstnameAsc(String searchKeyWord, int pageNo, int pageSize);
}
