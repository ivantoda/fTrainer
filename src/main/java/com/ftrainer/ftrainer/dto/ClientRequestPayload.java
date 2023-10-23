package com.ftrainer.ftrainer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestPayload {
    private Integer id;

    private Integer client;

    private Integer trainer;

    private String description;
}
