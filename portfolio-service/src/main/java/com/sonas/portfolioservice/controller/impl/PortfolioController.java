package com.sonas.portfolioservice.controller.impl;

import com.sonas.portfolioservice.controller.dto.PortfolioDTO;
import com.sonas.portfolioservice.dao.Portfolio;
import com.sonas.portfolioservice.repository.PortfolioRepository;
import com.sonas.portfolioservice.service.PortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/portfolios")
public class PortfolioController {

    private PortfolioService portfolioService;

    private PortfolioRepository portfolioRepository;

    public PortfolioController(PortfolioService portfolioService, PortfolioRepository portfolioRepository) {
        this.portfolioService = portfolioService;
        this.portfolioRepository = portfolioRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Portfolio> getCvs() {
        return portfolioRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Portfolio getPortfolioById(@PathVariable(name = "id") long id) {
        return portfolioRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio with id: " + id + " not found!"));
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Portfolio addPortfolio(@RequestBody @Valid PortfolioDTO portfolioDTO) {
        return portfolioService.create(portfolioDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePortfolio(@PathVariable(name = "id") long id) {
        portfolioRepository.deleteById(id);
    }
}
