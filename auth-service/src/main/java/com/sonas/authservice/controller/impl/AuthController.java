package com.sonas.authservice.controller.impl;

import com.sonas.authservice.controller.dto.TokenDTO;
import com.sonas.authservice.controller.dto.UserDTO;
import com.sonas.authservice.dao.User;
import com.sonas.authservice.service.MyUserDetailsService;
import com.sonas.authservice.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) throws Exception {

        System.out.println("Inside Login Controller");
        System.out.println(userDTO);
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getUsername(), userDTO.getPassword()));

        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("Bad Credentials");
        }catch (BadCredentialsException e)
        {
            e.printStackTrace();
            throw new Exception("Bad Credentials");
        }

        UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(userDTO.getUsername());

        String token = this.jwtUtils.generateToken(userDetails);
        System.out.println("JWT " + token);

        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping("/api/auth/register")
    public User register(@RequestBody UserDTO userDTO) {
        return myUserDetailsService.registerNewUser(userDTO);
    }
}
