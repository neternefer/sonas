package com.sonas.cvservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id")
    private long educationId;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "study_field")
    private String fieldOfStudy;

    private String degree;

    @Column(name = "cv_id")
    private long cvId;

    public Education(String schoolName,
                     LocalDate startDate,
                     LocalDate endDate,
                     String fieldOfStudy,
                     String degree,
                     long cvId) {
        this.schoolName = schoolName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fieldOfStudy = fieldOfStudy;
        this.degree = degree;
        this.cvId = cvId;
    }
}
