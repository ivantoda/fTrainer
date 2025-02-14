package com.ftrainer.ftrainer.repositories;

import com.ftrainer.ftrainer.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByUserRole_name(String roleName);

    Page<User> findByUserRole_name(String role, Pageable pageable);
    List<User> findByUserRole_nameOrderByLastnameAsc(String roleName);
    List<User> findByUserRole_nameOrderByLastnameDesc(String roleName);
    Optional<User> findById(Integer id);

    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Page<User> findByUsernameContainingIgnoreCaseOrFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(String searchKeyword, String searchKeyword1,String searchKeyword2, Pageable pageable);

}
