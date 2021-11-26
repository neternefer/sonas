package com.sonas.cvservice.controller.impl;

import com.sonas.cvservice.controller.dto.CvDTO;
import com.sonas.cvservice.controller.dto.EducationDTO;
import com.sonas.cvservice.dao.Cv;
import com.sonas.cvservice.dao.Education;
import com.sonas.cvservice.repository.EducationRepository;
import com.sonas.cvservice.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/curriculums/education")
public class EducationController {

    @Autowired
    EducationService educationService;

    @Autowired
    EducationRepository educationRepository;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<Education> getSchools() {
        return educationRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Education getSchoolById(@PathVariable(name = "id") long id) {
        return educationRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School with id: " + id + " not found!"));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Education> getSchoolsByCv(@RequestParam(value="cvId")  long cvId) {
        return educationService.getByCv(cvId);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Education updateSchool(@PathVariable(name = "id") long id,
                                  @RequestBody EducationDTO edu) {
        return educationService.update(id,edu);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Education addSchool(@RequestBody @Valid EducationDTO edu) {
        return educationService.create(edu);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSchool(@PathVariable(name = "id") long id) {
        educationRepository.deleteById(id);
    }
}