package com.sonas.cvservice.controller.dto;

import com.sonas.cvservice.dao.Education;
import com.sonas.cvservice.dao.Experience;
import com.sonas.cvservice.dao.Technology;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CvDTO {

    private long userId;

    private String cvType;

    private long addressId;

    private long socialId;

    private String hobby;

    private String jobTitle;

    private String seniority;

    private String intro;

    private List<Education> education;

    private List<Experience> experience;

    private List<Technology> technology;

    public CvDTO(long userId, String cvType, long addressId, long socialId,
                 String hobby, String jobTitle, String seniority, String intro) {
        this.userId = userId;
        this.cvType = cvType;
        this.addressId = addressId;
        this.socialId = socialId;
        this.hobby = hobby;
        this.jobTitle = jobTitle;
        this.seniority = seniority;
        this.intro = intro;
    }
}
