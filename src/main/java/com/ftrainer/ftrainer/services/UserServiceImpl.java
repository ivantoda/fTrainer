package com.ftrainer.ftrainer.services;

import com.ftrainer.ftrainer.dto.UserPayload;
import com.ftrainer.ftrainer.entities.User;
import com.ftrainer.ftrainer.repositories.RoleRepository;
import com.ftrainer.ftrainer.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.text.ParseException;


@Getter
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
    @Override
    public User addUser(UserPayload userPayload) throws ParseException {
        var bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User();

        user.setUsername(userPayload.getUsername());
        user.setFirstname(userPayload.getFirstname());
        user.setLastname(userPayload.getLastname());
        user.setEmail(userPayload.getEmail());

        var hashedPassword = (userPayload.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(hashedPassword));

        user.setDateOfBirth(userPayload.getDateOfBirth());

        var roleUser = roleRepository.findByName(userPayload.getUserRole());
        user.setUserRole(roleUser);
        user.setEnabled(true);
        return user;
    }

    @Override
    public void editUserById(Integer id, UserPayload userPayload) {
        var optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();


            user.setId(userPayload.getId());
            user.setUsername(userPayload.getUsername());
            user.setFirstname(userPayload.getFirstname());
            user.setLastname(userPayload.getLastname());
            user.setEmail(userPayload.getEmail());
            if (userPayload.getPassword() != null) {
                user.setPassword(userPayload.getPassword());
            }
            user.setDateOfBirth(userPayload.getDateOfBirth());
            var roleUser = roleRepository.findByName(userPayload.getUserRole());
            user.setUserRole(roleUser);
            user.setEnabled(true);


            userRepository.save(user);
        } else {
            throw new EntityNotFoundException("User with ID " + id + " not found");
        }
    }
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}

