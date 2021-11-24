package com.sonas.userservice.service;

import com.sonas.userservice.controller.dto.UserDTO;
import com.sonas.userservice.dao.User;
import com.sonas.userservice.enums.UserType;
import com.sonas.userservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User update(long id, UserDTO user) {
        User newUser = userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: " + id + " not found!"));
        if (user.getEmail() != null ) {
            newUser.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            newUser.setPassword(user.getPassword());
        }
        if (user.getUserType() != null) {
            newUser.setUserType(UserType.valueOf(user.getUserType()));
        }
        return userRepository.save(newUser);
    }

    public User create(UserDTO user) {
        User newUser = new User(user.getEmail(),
                                user.getPassword(),
                                UserType.valueOf(user.getUserType())
        );
        return userRepository.save(newUser);
    }
}
