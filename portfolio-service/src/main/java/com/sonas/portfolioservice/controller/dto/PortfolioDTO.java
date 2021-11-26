package com.sonas.portfolioservice.controller.dto;

import com.sonas.portfolioservice.dao.MinTechnology;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDTO {

    private long userId;

    private String portfolioType;

    private String photoName;

    private String aboutMe;

    private List<MinTechnology> minTech;

    public PortfolioDTO(long userId, String portfolioType, String photoName, String aboutMe) {
        this.userId = userId;
        this.portfolioType = portfolioType;
        this.photoName = photoName;
        this.aboutMe = aboutMe;
    }
}
