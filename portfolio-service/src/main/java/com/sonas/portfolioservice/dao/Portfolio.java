package com.sonas.portfolioservice.dao;

import com.sonas.portfolioservice.enums.PortfolioType;
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
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private long portfolioId;

    private long userId; //change to userDetails

    private PortfolioType portfolioType;

    private String aboutMe;

    public Portfolio(long userId, PortfolioType portfolioType) {
        this.userId = userId;
        this.portfolioType = portfolioType;
    }

    public Portfolio(long userId, PortfolioType portfolioType, String aboutMe) {
        this.userId = userId;
        this.portfolioType = portfolioType;
        this.aboutMe = aboutMe;
    }
}
