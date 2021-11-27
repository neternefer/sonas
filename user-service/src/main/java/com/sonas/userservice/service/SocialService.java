package com.sonas.userservice.service;

import com.sonas.userservice.controller.dto.SocialDTO;
import com.sonas.userservice.dao.Address;
import com.sonas.userservice.dao.Contact;
import com.sonas.userservice.dao.Social;
import com.sonas.userservice.repository.SocialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class SocialService {

    @Autowired
    SocialRepository socialRepository;

    public Social update(long id, SocialDTO social) throws MalformedURLException {
        Social foundSocial = socialRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Social links with id: " + id + " not found!"));
        foundSocial.setLinkType(social.getLinkType());
        foundSocial.setLink(new URL(social.getLink()));
        foundSocial.setContactId(social.getContactId());
        return socialRepository.save(foundSocial);
    }

    public Social create(SocialDTO social) throws MalformedURLException {
        Social newSocial = new Social(social.getLinkType(),
                                      new URL(social.getLink()),
                                      social.getContactId());
        return socialRepository.save(newSocial);
    }

    public List<Social> getByContact(long contactId) {
        List<Social> foundSocial = socialRepository.findByContactId(contactId);
        if(foundSocial.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Social links not found!");
        }
        return foundSocial;
    }
}
