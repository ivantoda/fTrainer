package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.User;
import com.ftrainer.ftrainer.repositories.RoleRepository;
import com.ftrainer.ftrainer.repositories.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        return userRepository.findByUserRole_nameOrderByLastnameAsc("TRAINER");
    }


    @Override
    public List<User> getTrainersDesc() {
        return userRepository.findByUserRole_nameOrderByLastnameDesc("TRAINER");
    }

    @Override
    public Page<User> findAllTrainersSortedByFirstnameDesc(String searchKeyWord, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("firstname").descending());
        if (searchKeyWord != null && !searchKeyWord.isBlank()) {
            return userRepository.searchTrainers("TRAINER", searchKeyWord, pageable);
        }
        return userRepository.findByUserRole_name("TRAINER", pageable);
    }

    @Override
    public Page<User> findAllTrainersSortedByFirstnameAsc(String searchKeyWord, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("firstname").ascending());
        if (searchKeyWord != null && !searchKeyWord.isBlank()) {
            return userRepository.searchTrainers("TRAINER", searchKeyWord, pageable);
        }
        return userRepository.findByUserRole_name("TRAINER", pageable);
    }
}
