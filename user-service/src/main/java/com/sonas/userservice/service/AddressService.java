package com.sonas.userservice.service;

import com.sonas.userservice.controller.dto.AddressDTO;
import com.sonas.userservice.dao.Address;
import com.sonas.userservice.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public Address update(long id, AddressDTO address) {
        Address foundAddress = addressRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address with id: " + id + " not found!"));
        foundAddress.setStreet(address.getStreet());
        foundAddress.setStreetNumber(address.getStreetNumber());
        foundAddress.setCity(address.getCity());
        foundAddress.setCountry(address.getCountry());
        return addressRepository.save(foundAddress);
    }

    public Address create(AddressDTO address) {
        Address newAddress = new Address(address.getStreet(),
                                         address.getStreetNumber(),
                                         address.getCity(),
                                         address.getCountry());
        return addressRepository.save(newAddress);
    }
}
