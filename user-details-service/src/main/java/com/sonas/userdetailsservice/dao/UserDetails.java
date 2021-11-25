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

    public UserDetails(long userId, String name, String lastName, Date dateOfBirth, long addressId,
                       Education education, Social social, Technology technology, Experience experience,
                       String hobby, String phone, JobTitle jobTitle, Seniority seniority) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.addressId = addressId;
        this.education = education;
        this.social = social;
        this.technology = technology;
        this.experience = experience;
        this.hobby = hobby;
        this.phone = phone;
        this.jobTitle = jobTitle;
        this.seniority = seniority;
    }
}

