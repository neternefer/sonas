package com.sonas.userservice.controller.impl;

import com.sonas.userservice.controller.dto.UserDTO;
import com.sonas.userservice.controller.interf.IUserController;
import com.sonas.userservice.dao.User;
import com.sonas.userservice.enums.UserType;
import com.sonas.userservice.repository.UserRepository;
import com.sonas.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/users")
public class UserController implements IUserController {

    private UserService userService;

    private UserRepository userRepository;

    public UserController(UserService userService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable(name = "id") long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: " + id + " not found!"));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUserByType(@RequestParam(value="userType") UserType userType) {
        return userService.getByType(userType);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public User updateUser(@PathVariable(name = "id") long id,
                                   @RequestBody UserDTO user) {
        return userService.update(id, user);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody @Valid UserDTO user) {
        return userService.create(user);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable(name = "id") long id) {
        userRepository.deleteById(id);
    }
}
