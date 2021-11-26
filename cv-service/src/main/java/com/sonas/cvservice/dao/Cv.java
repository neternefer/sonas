package com.sonas.cvservice.dao;

import com.sonas.cvservice.enums.CvType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cv_id")
    private long cvId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "cv_type")
    private CvType cvType;

    @Column(name = "dob")
    private Date dateOfBirth; //optional

    @Column(name = "address_id")
    private long addressId;

    @Column(name = "social_id")
    private long socialId;

    private String hobby;

    @Column(name = "job_title")
    private String jobTitle;

    private String seniority;

    private String intro;

    @OneToMany
    private List<Education> education;

    @OneToMany
    private List<Experience> experience;

    @OneToMany
    private List<Technology> technology;
}
