package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.User;
import javassist.NotFoundException;

import java.util.List;

public interface AdminService {
    List<User> getClients();
    List<User> getTrainers();

    public User getUserById(Integer id) throws NotFoundException;

    void deleteUserById(Integer id);


}
