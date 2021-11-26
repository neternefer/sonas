package com.sonas.cvservice.service;

import com.sonas.cvservice.controller.dto.TechnologyDTO;
import com.sonas.cvservice.dao.Technology;
import com.sonas.cvservice.repository.TechnologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TechnologyService {

    @Autowired
    TechnologyRepository technologyRepository;

    public Technology update(long id, TechnologyDTO tech) {
        Technology foundTech = technologyRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Technology with id: " + id + " not found!"));
        foundTech.setName(tech.getName());
        foundTech.setCvId(tech.getCvId());
        return technologyRepository.save(foundTech);
    }

    public Technology create(TechnologyDTO tech) {
        Technology newTech = new Technology(tech.getName(),
                                            tech.getCvId());
        return technologyRepository.save(newTech);
    }

    public List<Technology> getByCv(long cvId) {
        List<Technology> foundTech = technologyRepository.findByCvId(cvId);
        if(foundTech.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Technology not found!");
        }
        return foundTech;
    }
}
