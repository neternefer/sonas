package com.sonas.authservice.service;

import com.sonas.authservice.controller.dto.UserDTO;
import com.sonas.authservice.dao.MyUserDetails;
import com.sonas.authservice.dao.User;
import com.sonas.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User not found !!");
        } else {
            return new MyUserDetails(user);
        }
    }

    public User registerNewUser(UserDTO userDTO) {
        User user = this.userRepository.findByUsername(userDTO.getUsername());
        if (user != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }
        User newUser = new User(userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getUserRole());
        this.userRepository.save(newUser);
        return newUser;
    }
}
