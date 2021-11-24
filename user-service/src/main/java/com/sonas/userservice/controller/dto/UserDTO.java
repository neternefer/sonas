package com.sonas.userservice.controller.dto;

import com.sonas.userservice.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String email;

    private String password;

    private UserType userType;

    public UserDTO(String email, UserType userType) {
        this.email = email;
        this.userType = userType;
    }
}
