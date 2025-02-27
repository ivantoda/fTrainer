package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.dto.ClientRequestPayload;
import com.ftrainer.ftrainer.entities.ClientRequest;
import com.ftrainer.ftrainer.entities.Exercise;
import com.ftrainer.ftrainer.entities.User;

import java.util.List;


public interface ClientRequestService {

    ClientRequest createRequest(User trainer, User client, ClientRequestPayload clientRequestPayload);

    List<ClientRequest> findAll();

    ClientRequest findById(Integer id);

    List<ClientRequest> getClientRequestsByTrainer(User trainer);

    void delete(ClientRequest clientRequest);

    void setAsInactive(Integer requestId);
}
