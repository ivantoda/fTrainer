package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.dto.UserPayload;
import com.ftrainer.ftrainer.entities.User;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface UserService {
    User addUser(UserPayload userPayload) throws ParseException;
    User findById(Integer id);

    UserPayload findById2(int id);
    User findByUsername(String username);
    void editUserById(Integer id, UserPayload userPayload);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    List<User> findAllClients();

    UserPayload getUserForEdit(Integer id);
}
