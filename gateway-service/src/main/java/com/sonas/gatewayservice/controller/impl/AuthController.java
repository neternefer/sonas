package com.sonas.gatewayservice.controller.impl;

import com.sonas.gatewayservice.controller.dto.TokenDTO;
import com.sonas.gatewayservice.controller.dto.UserDTO;
import com.sonas.gatewayservice.dao.User;
import com.sonas.gatewayservice.service.MyUserDetailsService;
import com.sonas.gatewayservice.utils.JwtUtils;
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

    public AuthController(AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtUtils = jwtUtils;
    }

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
