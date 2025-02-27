package com.ftrainer.ftrainer.repositories;

import com.ftrainer.ftrainer.entities.ClientRequest;
import com.ftrainer.ftrainer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRequestRepository extends JpaRepository<ClientRequest, Integer> {
    List<ClientRequest> findByTrainerAndIsActiveTrue(User trainer);
}
