package com.sonas.userdetailsservice.dao;

import com.sonas.userdetailsservice.enums.JobTitle;
import com.sonas.userdetailsservice.enums.Seniority;
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
@Entity
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private long detailId;

    private long userId;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "dob")
    private Date dateOfBirth; //optional

    private long addressId;

    @OneToMany
    private Education education;

    @OneToMany
    private Social social;

    @OneToMany
    private Technology technology;

    @OneToMany
    private Experience experience;

    private String hobby;

    private String phone;

    @Enumerated
    private JobTitle jobTitle;

    @Enumerated
    private Seniority seniority;
}

