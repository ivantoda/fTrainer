package com.ftrainer.ftrainer.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradePayload {
    private Integer id;

    private Integer client;

    private Integer trainer;

    private Integer grade;

    private String comment;
}
