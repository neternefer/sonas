package com.sonas.userservice.service;

import com.sonas.userservice.controller.dto.UserDTO;
import com.sonas.userservice.dao.User;
import com.sonas.userservice.enums.UserType;
import com.sonas.userservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User update(long id, UserDTO user) {
        User foundUser = userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: " + id + " not found!"));
        foundUser.setEmail(user.getEmail());
        foundUser.setPassword(user.getPassword());
        foundUser.setName(user.getName());
        foundUser.setLastName(user.getLastName());
        foundUser.setUsername(user.getUsername());
        foundUser.setUserType(UserType.valueOf(user.getUserType()));
        return userRepository.save(foundUser);
    }

    public User create(UserDTO user) {
        User newUser = new User(user.getEmail(),
                                user.getPassword(),
                                user.getName(),
                                user.getLastName(),
                                user.getUsername(),
                                UserType.valueOf(user.getUserType())
        );
        return userRepository.save(newUser);
    }

    public List<User> getByType(UserType userType) {
        List<User> foundUsers = userRepository.findByUserType(userType);
        if(foundUsers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Users not found!");
        }
        return foundUsers;
    }
}
