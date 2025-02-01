package com.ftrainer.ftrainer.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name="grade")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="trainer_id", referencedColumnName = "id")
    @ToString.Exclude
    private User trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_id", referencedColumnName = "id")
    @ToString.Exclude
    private User client;

    @Column(name="grade", nullable = false)
    @NotNull(message = "Grade shouldn't be null")
    private Integer grade;

    @Column(name="comment")
    private String comment;

}
