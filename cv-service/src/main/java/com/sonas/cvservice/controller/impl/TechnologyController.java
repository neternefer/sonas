package com.sonas.cvservice.controller.impl;

import com.sonas.cvservice.controller.dto.TechnologyDTO;
import com.sonas.cvservice.dao.Technology;
import com.sonas.cvservice.repository.TechnologyRepository;
import com.sonas.cvservice.service.TechnologyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/curriculums/tech")
public class TechnologyController {

    private TechnologyRepository technologyRepository;

    private TechnologyService technologyService;

    public TechnologyController(TechnologyRepository technologyRepository,
                                TechnologyService technologyService) {
        this.technologyRepository = technologyRepository;
        this.technologyService = technologyService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Technology> getTech() {
        return technologyRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Technology getTechById(@PathVariable(name = "id") long id) {
        return technologyRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Technology with id: " + id + " not found!"));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Technology updateTech(@PathVariable(name = "id") long id,
                                 @RequestBody TechnologyDTO tech) {
        return technologyService.update(id, tech);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Technology addTech(@RequestBody @Valid TechnologyDTO tech) {
        return technologyService.create(tech);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTech(@PathVariable(name = "id") long id) {
        technologyRepository.deleteById(id);
    }
}
