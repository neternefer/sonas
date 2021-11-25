package com.sonas.userdetailsservice.controller.impl;

import com.sonas.userdetailsservice.controller.dto.UserDetailsDTO;
import com.sonas.userdetailsservice.dao.UserDetails;
import com.sonas.userdetailsservice.repository.UserDetailsRepository;
import com.sonas.userdetailsservice.service.UserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/details")
public class UserDetailsController {

    private UserDetailsService userDetailsService;

    private UserDetailsRepository userDetailsRepository;

    public UserDetailsController(UserDetailsService userDetailsService, UserDetailsRepository userDetailsRepository) {
        this.userDetailsService = userDetailsService;
        this.userDetailsRepository = userDetailsRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDetails> getUserDetails() {
        return userDetailsRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDetails getUserDetailsById(@PathVariable(name = "id") long id) {
        return userDetailsRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UserDetails with id: " + id + " not found!"));
    }

    @GetMapping(value = "userId")
    @ResponseStatus(HttpStatus.OK)
    public UserDetails getUserDetailsByUserId(@PathVariable long userId) {
        return userDetailsRepository.findByUserId(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UserDetails for user with id: " + userId + " not found!"));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserDetails updateUserDetails(@PathVariable(name = "id") long id,
                           @RequestBody UserDetailsDTO userDetails) {
        return userDetailsService.update(id, userDetails);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDetails addUserDetails(@RequestBody @Valid UserDetailsDTO userDetails) {
        return userDetailsService.create(userDetails);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserDetails(@PathVariable(name = "id") long id) {
        userDetailsRepository.deleteById(id);
    }

}
