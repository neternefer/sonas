package com.sonas.cvservice.dao;

import com.sonas.cvservice.enums.CvType;
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
public class Cv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cv_id")
    private long cvId;

    private long userId;

    private CvType cvType;

    public Cv(long userId, CvType cvType) {
        this.userId = userId;
        this.cvType = cvType;
    }
}
