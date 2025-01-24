package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.entities.User;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {
    List<User> getClients();
    List<User> getTrainers();

    public User getUserById(Integer id) throws NotFoundException;

    void deleteUserById(Integer id);

    Page<User> findPaginated(int pageNo, int pageSize);

    Page<User> findAllClientsSortedByLastNameDesc(int pageNo, int pageSize);

    Page<User> findAllClientsSortedByLastNameAsc(int pageNo, int pageSize);
}
