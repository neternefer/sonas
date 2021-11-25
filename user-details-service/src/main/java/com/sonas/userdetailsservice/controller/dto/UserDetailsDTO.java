package com.sonas.userdetailsservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {

    private long detailId;

    private long userId;

    private String name;

    private String lastName;

    private Date dateOfBirth; //optional

    private long addressId;

    private String phone;

}
