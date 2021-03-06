package com.sonas.userservice.controller.dto;

import com.sonas.userservice.dao.Address;
import com.sonas.userservice.dao.Social;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    private String phone;

    private List<Social> social;

    private List<Address> address;

    public ContactDTO(String phone) {
        this.phone = phone;
    }
}
