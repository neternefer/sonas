package com.sonas.userservice.controller.impl;

import com.sonas.userservice.controller.dto.AddressDTO;
import com.sonas.userservice.controller.dto.ContactDTO;
import com.sonas.userservice.dao.Address;
import com.sonas.userservice.dao.Contact;
import com.sonas.userservice.repository.AddressRepository;
import com.sonas.userservice.repository.ContactRepository;
import com.sonas.userservice.service.AddressService;
import com.sonas.userservice.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/users/contact")
public class ContactController {

    private ContactRepository contactRepository;

    private ContactService contactService;

    public ContactController(ContactRepository contactRepository, ContactService contactService) {
        this.contactRepository = contactRepository;
        this.contactService = contactService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Contact getContactById(@PathVariable(name = "id") long id) {
        return contactRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact with id: " + id + " not found!"));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Contact updateContact(@PathVariable(name = "id") long id,
                                 @RequestBody ContactDTO contact) {
        return contactService.update(id, contact);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Contact addContact(@RequestBody @Valid ContactDTO contact) {
        return contactService.create(contact);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteContact(@PathVariable(name = "id") long id) {
        contactRepository.deleteById(id);
    }
}
