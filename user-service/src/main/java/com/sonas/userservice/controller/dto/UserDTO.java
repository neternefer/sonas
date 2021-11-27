package com.sonas.userservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String name;

    private String lastName;

    private String username;

    private String userType;

    private long contactId;
}
