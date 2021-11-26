package com.sonas.cvservice.service;

import com.sonas.cvservice.controller.dto.CvDTO;
import com.sonas.cvservice.dao.Cv;
import com.sonas.cvservice.enums.CvType;
import com.sonas.cvservice.repository.CvRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CvService {

    private CvRepository cvRepository;

    public CvService(CvRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    public Cv create(CvDTO cvDTO) {
        Cv newCv = new Cv(cvDTO.getUserId(),
                          CvType.valueOf(cvDTO.getCvType()),
                          cvDTO.getAddressId(),
                          cvDTO.getSocialId(),
                          cvDTO.getHobby(),
                          cvDTO.getJobTitle(),
                          cvDTO.getSeniority(),
                          cvDTO.getIntro());
        return cvRepository.save(newCv);
    }

    public Cv update(long id, CvDTO cv) {
        Cv newCv = cvRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cv with id: " + id + " not found!"));
        newCv.setUserId(cv.getUserId());
        newCv.setCvType(CvType.valueOf(cv.getCvType()));
        newCv.setAddressId(cv.getAddressId());
        newCv.setSocialId(cv.getSocialId());
        newCv.setJobTitle(cv.getJobTitle());
        newCv.setSeniority(cv.getSeniority());
        newCv.setIntro(cv.getIntro());
        newCv.setEducation(cv.getEducation());
        newCv.setExperience(cv.getExperience());
        newCv.setTechnology(cv.getTechnology());
        return cvRepository.save(newCv);
    }
}
