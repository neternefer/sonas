package com.sonas.portfolioservice.service;

import com.sonas.portfolioservice.controller.dto.MinTechnologyDTO;
import com.sonas.portfolioservice.dao.MinTechnology;
import com.sonas.portfolioservice.repository.MinTechRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MinTechnologyService {

    private MinTechRepository minTechnologyRepository;

    public MinTechnologyService(MinTechRepository minTechnologyRepository) {
        this.minTechnologyRepository = minTechnologyRepository;
    }

    public MinTechnology update(long id, MinTechnologyDTO minTech) {
        MinTechnology foundTech = minTechnologyRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Technology with id: " + id + " not found!"));
        foundTech.setName(minTech.getName());
        foundTech.setPortfolioId(minTech.getPortfolioId());
        return minTechnologyRepository.save(foundTech);
    }

    public MinTechnology create(MinTechnologyDTO minTech) {
        MinTechnology newTech = new MinTechnology(minTech.getName(),
                                                  minTech.getPortfolioId()
        );
        return minTechnologyRepository.save(newTech);
    }

    public List<MinTechnology> getByPortfolio(long portfolioId) {
        List<MinTechnology> foundTech = minTechnologyRepository.findByPortfolioId(portfolioId);
        if(foundTech.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Technology not found!");
        }
        return foundTech;
    }
}
