package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.dto.ClientRequestPayload;
import com.ftrainer.ftrainer.entities.ClientRequest;
import com.ftrainer.ftrainer.entities.Exercise;
import com.ftrainer.ftrainer.entities.User;

import java.util.List;


public interface ClientRequestService {

    ClientRequest createRequest(ClientRequest clientRequest);

    List<ClientRequest> findAll();

    List<ClientRequest> getClientRequestsByTrainer(User trainer);
}
