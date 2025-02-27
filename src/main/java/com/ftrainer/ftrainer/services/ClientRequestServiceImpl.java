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
import java.util.Optional;


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
    public ClientRequest createRequest(User trainer, User client, ClientRequestPayload clientRequestPayload) {
        ClientRequest clientRequest = new ClientRequest();

        clientRequest.setId(clientRequestPayload.getId());
        clientRequest.setTrainer(trainer);
        clientRequest.setClient(client);
        clientRequest.setDescription(clientRequestPayload.getDescription());
        clientRequest.setActive(true);

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
        return clientRequestRepository.findByTrainerAndIsActiveTrue(trainer);
    }

    @Override
    public void delete(ClientRequest clientRequest) {
        clientRequestRepository.delete(clientRequest);
    }

    public void setAsInactive(Integer clientRequestId){
        Optional<ClientRequest> optionalClientRequest = clientRequestRepository.findById(clientRequestId);
        if(optionalClientRequest.isPresent()){
            ClientRequest clientRequest = optionalClientRequest.get();
            clientRequest.setActive(false);
            clientRequestRepository.save(clientRequest);
        }
        else{
            System.out.println("No such Client Request");
        }
    }

}
