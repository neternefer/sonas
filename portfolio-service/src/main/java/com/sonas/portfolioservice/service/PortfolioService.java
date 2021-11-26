package com.sonas.portfolioservice.service;

import com.sonas.portfolioservice.controller.dto.PortfolioDTO;
import com.sonas.portfolioservice.dao.Portfolio;
import com.sonas.portfolioservice.enums.PortfolioType;
import com.sonas.portfolioservice.repository.PortfolioRepository;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

    private PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public Portfolio create(PortfolioDTO portfolioDTO) {
        Portfolio newPortfolio = new Portfolio(portfolioDTO.getUserId(),
                                     PortfolioType.valueOf(portfolioDTO.getPortfolioType()));
        return portfolioRepository.save(newPortfolio);
    }
}
