package com.ftrainer.ftrainer.repositories;

import com.ftrainer.ftrainer.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    List<User> findByUserRole_name(String roleName);

    Page<User> findByUserRole_name(String role, Pageable pageable);
    List<User> findByUserRole_nameOrderByFirstnameAsc(String roleName);
    List<User> findByUserRole_nameOrderByFirstnameDesc(String roleName);
    Optional<User> findById(Integer id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);



}
