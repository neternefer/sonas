package com.sonas.cvservice.service;

import com.sonas.cvservice.controller.dto.EducationDTO;
import com.sonas.cvservice.dao.Education;
import com.sonas.cvservice.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EducationService {

    @Autowired
    EducationRepository educationRepository;

    public Education update(long id, EducationDTO edu) {
        Education foundEdu = educationRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School with id: " + id + " not found!"));
        foundEdu.setSchoolName(edu.getSchoolName());
        foundEdu.setStartDate(edu.getStartDate());
        foundEdu.setEndDate(edu.getEndDate());
        foundEdu.setFieldOfStudy(edu.getFieldOfStudy());
        foundEdu.setDegree(edu.getDegree());
        foundEdu.setCvId((edu.getCvId()));
        return educationRepository.save(foundEdu);
    }

    public Education create(EducationDTO edu) {
        Education newEdu = new Education(edu.getSchoolName(),
                                         edu.getStartDate(),
                                         edu.getEndDate(),
                                         edu.getFieldOfStudy(),
                                         edu.getDegree(),
                                         edu.getCvId());
        return educationRepository.save(newEdu);
    }
}
