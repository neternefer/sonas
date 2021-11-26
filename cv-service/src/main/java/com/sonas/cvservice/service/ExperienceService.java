package com.sonas.cvservice.service;

import com.sonas.cvservice.controller.dto.ExperienceDTO;
import com.sonas.cvservice.dao.Education;
import com.sonas.cvservice.dao.Experience;
import com.sonas.cvservice.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ExperienceService {

    @Autowired
    ExperienceRepository experienceRepository;

    public Experience update(long id, ExperienceDTO exp) {
        Experience foundExp = experienceRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Experience with id: " + id + " not found!"));
        foundExp.setPosition(exp.getPosition());
        foundExp.setCompany(exp.getCompany());
        foundExp.setStartDate(exp.getStartDate());
        foundExp.setEndDate(exp.getEndDate());
        foundExp.setDuties(exp.getDuties());
        foundExp.setCvId(exp.getCvId());
        return experienceRepository.save(foundExp);
    }

    public Experience create(ExperienceDTO exp) {
        Experience newExp = new Experience(exp.getPosition(),
                                           exp.getCompany(),
                                           exp.getStartDate(),
                                           exp.getEndDate(),
                                           exp.getDuties(),
                                           exp.getCvId());
        return experienceRepository.save(newExp);
    }

    public List<Experience> getByCv(long cvId) {
        List<Experience> foundExp = experienceRepository.findByCvId(cvId);
        if(foundExp.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Experience not found!");
        }
        return foundExp;
    }
}
