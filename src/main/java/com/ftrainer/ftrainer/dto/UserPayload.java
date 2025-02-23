package com.ftrainer.ftrainer.dto;
import com.ftrainer.ftrainer.entities.User;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserPayload {
    private Integer id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private String userRole;
    private boolean isEnabled;
    public UserPayload(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.dateOfBirth = user.getDateOfBirth();
        this.userRole = String.valueOf(user.getUserRole());
        this.isEnabled = user.isEnabled();
    }
}
