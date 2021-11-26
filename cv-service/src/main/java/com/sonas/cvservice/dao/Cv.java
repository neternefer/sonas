package com.sonas.cvservice.dao;

import com.sonas.cvservice.enums.CvType;
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
public class Cv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cv_id")
    private long cvId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "cv_type")
    private CvType cvType;

    @Column(name = "address_id")
    private long addressId;

    @Column(name = "social_id")
    private long socialId;

    private String hobby;

    @Column(name = "job_title")
    private String jobTitle;

    private String seniority;

    private String intro;

    @OneToMany(mappedBy = "cvId")
    private List<Education> education;

    @OneToMany(mappedBy = "cvId")
    private List<Experience> experience;

    @OneToMany(mappedBy = "cvId")
    private List<Technology> technology;

    public Cv(long userId,
              CvType cvType,
              long addressId,
              long socialId,
              String hobby,
              String jobTitle,
              String seniority,
              String intro) {
        this.userId = userId;
        this.cvType = cvType;
        this.addressId = addressId;
        this.socialId = socialId;
        this.hobby = hobby;
        this.jobTitle = jobTitle;
        this.seniority = seniority;
        this.intro = intro;
    }

    public Cv(long userId,
              CvType cvType,
              long addressId,
              long socialId,
              String hobby,
              String jobTitle,
              String seniority,
              String intro,
              List<Education> education,
              List<Experience> experience,
              List<Technology> technology) {
        this.userId = userId;
        this.cvType = cvType;
        this.addressId = addressId;
        this.socialId = socialId;
        this.hobby = hobby;
        this.jobTitle = jobTitle;
        this.seniority = seniority;
        this.intro = intro;
        this.education = education;
        this.experience = experience;
        this.technology = technology;
    }
}
