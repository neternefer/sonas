package com.sonas.userservice.controller.impl;

import com.sonas.userservice.controller.dto.AddressDTO;
import com.sonas.userservice.dao.Address;
import com.sonas.userservice.repository.AddressRepository;
import com.sonas.userservice.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/users/address")
public class AddressController {

    private AddressRepository addressRepository;

    private AddressService addressService;

    public AddressController(AddressRepository addressRepository, AddressService addressService) {
        this.addressRepository = addressRepository;
        this.addressService = addressService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Address getAddressById(@PathVariable(name = "id") long id) {
        return addressRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address with id: " + id + " not found!"));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Address updateAddress(@PathVariable(name = "id") long id,
                           @RequestBody AddressDTO address) {
        return addressService.update(id, address);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Address addAddress(@RequestBody @Valid AddressDTO address) {
        return addressService.create(address);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAddress(@PathVariable(name = "id") long id) {
        addressRepository.deleteById(id);
    }
}
