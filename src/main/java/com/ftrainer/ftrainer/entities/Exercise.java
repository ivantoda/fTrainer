package com.ftrainer.ftrainer.entities;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "exercise")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "exercise_name shouldn't be null")
    private String name;

    @Column(name = "description", nullable = false)
    @NotNull(message = "exercise_description shouldn't be null")
    private String description;
}
