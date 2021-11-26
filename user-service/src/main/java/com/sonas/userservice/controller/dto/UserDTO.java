package com.sonas.userservice.controller.dto;

import com.sonas.userservice.enums.UserType;
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

    @NotNull
    private String userType;
}
