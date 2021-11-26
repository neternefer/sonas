package com.sonas.cvservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EducationDTO {

    private String schoolName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String fieldOfStudy;

    private String degree;

    private long cvId;
}
