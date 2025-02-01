package com.ftrainer.ftrainer.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false)
    @NotNull(message = "username shouldn't be null")
    private String username;

    @Column(name = "firstname", nullable = false)
    @NotNull(message = "firstname shouldn't be null")
    private String firstname;

    @Column(name = "lastname", nullable = false)
    @NotNull(message = "lastname shouldn't be null")
    private String lastname;

    @Column(name = "email", nullable = false)
    @NotNull(message = "email shouldn't be null")
    private String email;

    @Column(name = "password", nullable = false)
    @NotNull(message = "password shouldn't be null")
    private String password;

    @Column(name = "date_of_birth", nullable = false)
    @NotNull(message = "date_of_birth shouldn't be null")
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Role userRole;

    @Column(name = "is_enabled")
    public boolean isEnabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
