package com.ftrainer.ftrainer.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "set_exercise")
public class SetExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "set_count", nullable = false)
    @NotNull(message = "set_count shouldn't be null")
    private Integer setCount;

    @Column(name = "exercise_count", nullable = false)
    @NotNull(message = "exercise_count shouldn't be null")
    private Integer exerciseCount;

    @Column(name = "exercise_id", nullable = false)
    @NotNull(message = "exercise_id shouldn't be null")

    private Integer exerciseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    @ToString.Exclude
    private Program program;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SetExercise that = (SetExercise) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

