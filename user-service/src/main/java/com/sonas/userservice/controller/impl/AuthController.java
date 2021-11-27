package com.sonas.userservice.controller.impl;

import com.sonas.userservice.dao.JWTRequest;
import com.sonas.userservice.dao.JWTResponse;
import com.sonas.userservice.service.JwtUtil;
import com.sonas.userservice.service.MyUserDetailsService;
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
@RequestMapping("/api/users/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          MyUserDetailsService myUserDetailsService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> generateToken(@RequestBody JWTRequest jwtRequest) throws Exception {
        System.out.println("Inside Controller");
        System.out.println(jwtRequest);
        try {
            System.out.println("in try");
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()));
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            System.out.println("in not found catch ");
            throw new Exception("Bad Credentials");
        }catch (BadCredentialsException e)
        {
            e.printStackTrace();
            System.out.println("in bad credentials catch");
            throw new Exception("Bad Credentials");
        }
        System.out.println("after usth");
        UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        System.out.println("before token generation");
        String token = this.jwtUtil.generateToken(userDetails);
        System.out.println("After token generation");
        System.out.println("JWT " + token);
        return ResponseEntity.ok(new JWTResponse(token));
    }
}
