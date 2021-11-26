package com.sonas.portfolioservice.controller.impl;

import com.sonas.portfolioservice.controller.dto.MinTechnologyDTO;
import com.sonas.portfolioservice.controller.dto.PortfolioDTO;
import com.sonas.portfolioservice.dao.MinTechnology;
import com.sonas.portfolioservice.dao.Portfolio;
import com.sonas.portfolioservice.repository.MinTechRepository;
import com.sonas.portfolioservice.service.MinTechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/portfolios/mintech")
public class MinTechnologyController {

    @Autowired
    MinTechnologyService minTechnologyService;

    @Autowired
    MinTechRepository minTechnologyRepository;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<MinTechnology> getMinTech() {
        return minTechnologyRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MinTechnology getTechById(@PathVariable(name = "id") long id) {
        return minTechnologyRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Technology with id: " + id + " not found!"));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MinTechnology> getTechByCv(@RequestParam(value="portfolioId")  long portfolioId) {
        return minTechnologyService.getByPortfolio(portfolioId);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MinTechnology updateTech(@PathVariable(name = "id") long id,
                                    @RequestBody MinTechnologyDTO minTech) {
        return minTechnologyService.update(id, minTech);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public MinTechnology addTech(@RequestBody @Valid MinTechnologyDTO minTech) {
        return minTechnologyService.create(minTech);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTech(@PathVariable(name = "id") long id) {
        minTechnologyRepository.deleteById(id);
    }
}
