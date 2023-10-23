package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.User;
import com.ftrainer.ftrainer.repositories.RoleRepository;
import com.ftrainer.ftrainer.repositories.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
public class ClientServiceImpl implements ClientService{

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public ClientServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<User> getTrainers() {
        return userRepository.findByUserRole_name("TRAINER");
    }

    @Override
    public List<User> getTrainersAsc() {
        return userRepository.findByUserRole_nameOrderByFirstnameAsc("TRAINER");
    }


    @Override
    public List<User> getTrainersDesc() {
        return userRepository.findByUserRole_nameOrderByFirstnameDesc("TRAINER");
    }
}
