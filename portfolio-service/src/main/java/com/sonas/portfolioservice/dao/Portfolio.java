package com.sonas.portfolioservice.dao;

import com.sonas.portfolioservice.enums.PortfolioType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.net.URL;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private long portfolioId;

    private long userId;

    @Column(name = "portfolio_type")
    private PortfolioType portfolioType;

    @Column(name = "photo_name")
    private String photoName;

    @Column(name = "about_me")
    private String aboutMe;

    @Column(name = "min_tech")
    @OneToMany(mappedBy = "portfolioId")
    private List<MinTechnology> minTech;

    public Portfolio(long userId,
                     PortfolioType portfolioType,
                     String photoName,
                     String aboutMe,
                     List<MinTechnology> minTech) {
        this.userId = userId;
        this.portfolioType = portfolioType;
        this.photoName = photoName;
        this.aboutMe = aboutMe;
        this.minTech = minTech;
    }

    public Portfolio(long userId,
                     PortfolioType portfolioType,
                     String photoName,
                     String aboutMe) {
        this.userId = userId;
        this.portfolioType = portfolioType;
        this.photoName = photoName;
        this.aboutMe = aboutMe;
    }
}
