package com.sonas.cvservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tech_id")
    private long techId;

    private String name;

    @Column(name = "cv_id")
    private long cvId;

    public Technology(String name,
                      long cvId) {
        this.name = name;
        this.cvId = cvId;
    }
}
