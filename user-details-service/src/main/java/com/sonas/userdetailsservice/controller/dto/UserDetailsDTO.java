package com.sonas.userdetailsservice.controller.dto;

import com.sonas.userdetailsservice.dao.Education;
import com.sonas.userdetailsservice.dao.Experience;
import com.sonas.userdetailsservice.dao.Social;
import com.sonas.userdetailsservice.dao.Technology;
import com.sonas.userdetailsservice.enums.JobTitle;
import com.sonas.userdetailsservice.enums.Seniority;
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
public class UserDetailsDTO {

    private long userId;

    private String name;

    private String lastName;

    private Date dateOfBirth; //optional

    private long addressId;

    private List<Education> education;

    private List<Social> social;

    private List<Technology> technology;

    private List<Experience> experience;

    private String phone;

    private String hobby;

    @Enumerated
    private JobTitle jobTitle;

    @Enumerated
    private Seniority seniority;


}
