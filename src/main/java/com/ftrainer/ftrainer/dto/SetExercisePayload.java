package com.ftrainer.ftrainer.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetExercisePayload {

    private Integer id;

    private Integer setCount;

    private Integer exerciseCount;

    private Integer exerciseId;

}
