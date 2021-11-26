package com.sonas.cvservice.controller.impl;

import com.sonas.cvservice.controller.dto.ExperienceDTO;
import com.sonas.cvservice.dao.Experience;
import com.sonas.cvservice.repository.ExperienceRepository;
import com.sonas.cvservice.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/curriculums/exp")
public class ExperienceController {

    @Autowired
    ExperienceService experienceService;

    @Autowired
    ExperienceRepository experienceRepository;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<Experience> getExp() {
        return experienceRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Experience getExpById(@PathVariable(name = "id") long id) {
        return experienceRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Experience with id: " + id + " not found!"));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Experience> getExpByCv(@RequestParam(value="cvId")  long cvId) {
        return experienceService.getByCv(cvId);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Experience updateExp(@PathVariable(name = "id") long id,
                           @RequestBody ExperienceDTO exp) {
        return experienceService.update(id, exp);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Experience addExp(@RequestBody @Valid ExperienceDTO exp) {
        return experienceService.create(exp);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteExp(@PathVariable(name = "id") long id) {
        experienceRepository.deleteById(id);
    }
}
