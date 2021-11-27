package com.sonas.userservice.service;

import com.sonas.userservice.controller.dto.ContactDTO;
import com.sonas.userservice.dao.Address;
import com.sonas.userservice.dao.Contact;
import com.sonas.userservice.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    public Contact update(long id, ContactDTO contact) {
        Contact foundContact = contactRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact with id: " + id + " not found!"));
        foundContact.setPhone(contact.getPhone());
        foundContact.setSocial(contact.getSocial());
        foundContact.setAddress(contact.getAddress());
        return contactRepository.save(foundContact);
    }

    public Contact create(ContactDTO contact) {
        Contact newContact = new Contact(contact.getPhone(),
                                         contact.getSocial(),
                                         contact.getAddress());
        return contactRepository.save(newContact);
    }
}
