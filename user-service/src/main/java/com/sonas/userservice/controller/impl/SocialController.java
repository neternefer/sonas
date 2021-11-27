package com.sonas.userservice.controller.impl;

import com.sonas.userservice.controller.dto.ContactDTO;
import com.sonas.userservice.controller.dto.SocialDTO;
import com.sonas.userservice.dao.Contact;
import com.sonas.userservice.dao.Social;
import com.sonas.userservice.repository.ContactRepository;
import com.sonas.userservice.repository.SocialRepository;
import com.sonas.userservice.service.ContactService;
import com.sonas.userservice.service.SocialService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/users/social")
public class SocialController {

    private SocialRepository socialRepository;

    private SocialService socialService;

    public SocialController(SocialRepository socialRepository, SocialService socialService) {
        this.socialRepository = socialRepository;
        this.socialService = socialService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Social> getSocials() {
        return socialRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Social getSocialById(@PathVariable(name = "id") long id) {
        return socialRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Social links with id: " + id + " not found!"));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Social updateSocial(@PathVariable(name = "id") long id,
                                 @RequestBody SocialDTO social) throws MalformedURLException {
        return socialService.update(id, social);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Social addSocial(@RequestBody @Valid SocialDTO social) throws MalformedURLException {
        return socialService.create(social);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSocial(@PathVariable(name = "id") long id) {
        socialRepository.deleteById(id);
    }
}
