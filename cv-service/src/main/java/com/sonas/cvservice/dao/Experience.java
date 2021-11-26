package com.sonas.cvservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experience_id")
    private long experienceId;

    private String position;

    private String company;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private String duties;

    @Column(name = "cv_id")
    private long cvId;

    public Experience(String position,
                      String company,
                      LocalDate startDate,
                      LocalDate endDate,
                      String duties,
                      long cvId) {
        this.position = position;
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duties = duties;
        this.cvId = cvId;
    }
}
