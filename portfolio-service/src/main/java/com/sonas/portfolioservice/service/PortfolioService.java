package com.sonas.portfolioservice.service;

import com.sonas.portfolioservice.controller.dto.PortfolioDTO;
import com.sonas.portfolioservice.dao.Portfolio;
import com.sonas.portfolioservice.enums.PortfolioType;
import com.sonas.portfolioservice.repository.PortfolioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PortfolioService {

    private PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public Portfolio create(PortfolioDTO portfolioDTO) {
        Portfolio newPortfolio = new Portfolio(portfolioDTO.getUserId(),
                                               PortfolioType.valueOf(portfolioDTO.getPortfolioType()),
                                               portfolioDTO.getPhotoName(),
                                               portfolioDTO.getAboutMe());
        return portfolioRepository.save(newPortfolio);
    }

    public Portfolio update(long id, PortfolioDTO portfolio) {
        Portfolio foundPortfolio = portfolioRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio with id: " + id + " not found!"));
        foundPortfolio.setUserId(portfolio.getUserId());
        foundPortfolio.setPortfolioType(PortfolioType.valueOf(portfolio.getPortfolioType()));
        foundPortfolio.setPhotoName(portfolio.getPhotoName());
        foundPortfolio.setAboutMe(portfolio.getAboutMe());
        return portfolioRepository.save(foundPortfolio);
    }
}
