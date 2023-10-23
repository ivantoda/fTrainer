package com.ftrainer.ftrainer.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramPayload {
    private Integer id;

    private Integer trainerId;

    private Integer clientId;

    private List<Integer> setExerciseId;
}
