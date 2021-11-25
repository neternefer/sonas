package com.sonas.cvservice.controller.impl;

import com.sonas.cvservice.controller.dto.CvDTO;
import com.sonas.cvservice.dao.Cv;
import com.sonas.cvservice.repository.CvRepository;
import com.sonas.cvservice.service.CvService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/curriculums")
public class CvController {

    private CvService cvService;

    private CvRepository cvRepository;

    public CvController(CvService cvService, CvRepository cvRepository) {
        this.cvService = cvService;
        this.cvRepository = cvRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Cv> getCvs() {
        return cvRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cv getCvById(@PathVariable(name = "id") long id) {
        return cvRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cv with id: " + id + " not found!"));
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Cv addCv(@RequestBody @Valid CvDTO cv) {
        return cvService.create(cv);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCv(@PathVariable(name = "id") long id) {
        cvRepository.deleteById(id);
    }
}
