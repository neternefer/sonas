package com.sonas.userservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private long addressId;

    private String street;

    @Column(name = "street_number")
    private String streetNumber;

    private String city;

    private String country;

    @Column(name = "contact_id")
    private long contactId;

    public Address(String street,
                   String streetNumber,
                   String city,
                   String country,
                   long contactId) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.country = country;
        this.contactId = contactId;
    }
}
