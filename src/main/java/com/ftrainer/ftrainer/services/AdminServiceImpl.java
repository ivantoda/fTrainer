package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.User;
import com.ftrainer.ftrainer.repositories.RoleRepository;
import com.ftrainer.ftrainer.repositories.UserRepository;
import javassist.NotFoundException;
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
public class AdminServiceImpl implements AdminService{
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public User getUserById(Integer id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public List<User> getClients() {
        return userRepository.findByUserRole_name("CLIENT");
    }
    public List<User> getTrainers() {
        return userRepository.findByUserRole_name("TRAINER");
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

    @Override
    public Page<User> findPaginated(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.userRepository.findByUserRole_name("CLIENT",pageable);
    }

    public Page<User> findAllClientsSortedByLastNameAsc(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("Lastname").ascending());
        return userRepository.findByUserRole_name("CLIENT", pageable);
    }

    public Page<User> findAllClientsSortedByLastNameDesc(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("Lastname").descending());
        return userRepository.findByUserRole_name("CLIENT", pageable);
    }

}
