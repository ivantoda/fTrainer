package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.dto.ClientRequestPayload;
import com.ftrainer.ftrainer.entities.ClientRequest;
import com.ftrainer.ftrainer.entities.User;
import com.ftrainer.ftrainer.repositories.ClientRequestRepository;
import com.ftrainer.ftrainer.repositories.RoleRepository;
import com.ftrainer.ftrainer.repositories.UserRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Getter
public class ClientRequestServiceImpl implements ClientRequestService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final ClientRequestRepository clientRequestRepository;

    public ClientRequestServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ClientRequestRepository clientRequestRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.clientRequestRepository = clientRequestRepository;
    }

    @Override
    public ClientRequest createRequest(ClientRequest clientRequest) {
        return clientRequestRepository.save(clientRequest);
    }

    @Override
    public List<ClientRequest> findAll() {
        return clientRequestRepository.findAll();
    }

    @Override
    public ClientRequest findById(Integer id) {
        return clientRequestRepository.findById(id).orElse(null);
    }

    @Override
    public List<ClientRequest> getClientRequestsByTrainer(User trainer) {
        return clientRequestRepository.findByTrainer(trainer);
    }

    @Override
    public void delete(ClientRequest clientRequest) {
        clientRequestRepository.delete(clientRequest);
    }

}
