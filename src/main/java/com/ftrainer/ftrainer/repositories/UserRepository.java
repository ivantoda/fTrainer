package com.ftrainer.ftrainer.repositories;

import com.ftrainer.ftrainer.dto.UserPayload;
import com.ftrainer.ftrainer.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByUserRole_name(String roleName);

    Page<User> findByUserRole_name(String role, Pageable pageable);
    List<User> findByUserRole_nameOrderByLastnameAsc(String roleName);
    List<User> findByUserRole_nameOrderByLastnameDesc(String roleName);
    Optional<User> findById(Integer id);

    Optional<User> findById(int id);

    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Page<User> findByUsernameContainingIgnoreCaseOrFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(String searchKeyword, String searchKeyword1,String searchKeyword2, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.userRole r WHERE r.name = :role " +
            "AND (LOWER(u.username) LIKE LOWER(CONCAT('%', :searchKeyword, '%')) " +
            "OR LOWER(u.firstname) LIKE LOWER(CONCAT('%', :searchKeyword, '%')) " +
            "OR LOWER(u.lastname) LIKE LOWER(CONCAT('%', :searchKeyword, '%')))")
    Page<User> searchTrainers(@Param("role") String role,
                              @Param("searchKeyword") String searchKeyword,
                              Pageable pageable);
}
