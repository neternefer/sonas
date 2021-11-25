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

    public Cv create(CvDTO cv) {
        Cv newCv = new Cv(cv.getUserId(), CvType.valueOf(cv.getCvType()));
        return cvRepository.save(newCv);
    }
}
