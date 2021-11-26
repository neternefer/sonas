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
public class ExperienceDTO {

    private String position;

    private String company;

    private LocalDate startDate;

    private LocalDate endDate;

    private String duties;

    private long cvId;
}
