package com.sonas.userservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private long contactId;

    private String phone;

    @OneToMany(mappedBy = "contactId")
    private List<Social> social;

    @OneToMany(mappedBy = "contactId")
    private List<Address> address;

    public Contact(String phone, List<Social> social, List<Address> address) {
        this.phone = phone;
        this.social = social;
        this.address = address;
    }

    public Contact(String phone) {
        this.phone = phone;
    }
}
