package com.sonas.userservice.controller.dto;

import com.sonas.userservice.dao.Contact;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private String street;

    private String streetNumber;

    private String city;

    private String country;

    private long contactId;
}
