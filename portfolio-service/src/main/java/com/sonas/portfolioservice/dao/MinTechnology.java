package com.sonas.portfolioservice.dao;

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
public class MinTechnology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "min_tech_id")
    private long minTechId;

    private String name;

    @Column(name = "portfolio_id")
    private long portfolioId;

    public MinTechnology(String name, long portfolioId) {
        this.name = name;
        this.portfolioId = portfolioId;
    }
}
