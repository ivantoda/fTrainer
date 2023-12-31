package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.dto.UserPayload;
import com.ftrainer.ftrainer.entities.User;

import java.text.ParseException;

public interface UserService {
    User addUser(UserPayload userPayload) throws ParseException;
    User findById(Integer id);
    void editUserById(Integer id, UserPayload userPayload);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
