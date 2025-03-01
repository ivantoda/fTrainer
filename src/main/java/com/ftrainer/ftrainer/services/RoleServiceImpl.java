package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.Role;
import com.ftrainer.ftrainer.repositories.RoleRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getRoles(){
        return roleRepository.findAll().stream()
                .filter(role -> !role.getName().equals("ADMIN"))
                .collect(Collectors.toList());
    }
}
