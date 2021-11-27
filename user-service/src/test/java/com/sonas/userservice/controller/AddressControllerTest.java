package com.sonas.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonas.userservice.controller.dto.AddressDTO;
import com.sonas.userservice.dao.Address;
import com.sonas.userservice.dao.Contact;
import com.sonas.userservice.repository.AddressRepository;
import com.sonas.userservice.repository.ContactRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AddressControllerTest {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Address address;

    private Address address1;

    private List<Address> addresses = new ArrayList<>();

    private Contact contact;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        contact = new Contact("111222333");
        contactRepository.save(contact);
        address = new Address("Baker Str.", "11", "London",
                "United Kingdom", contact.getContactId());
        address1 = new Address("Maretta", "56", "Madrid",
                "Spain", contact.getContactId());
        addresses.addAll(List.of(address, address1));
        contact.setAddress(addresses);
        contactRepository.save(contact);
        addressRepository.saveAll(List.of(address, address1));
    }

    @AfterEach
    public  void tearDown() {
        addressRepository.deleteAll();
        contactRepository.deleteAll();
    }

    @Test
    void getAddresses() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users/address/get"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("London"));
        assertTrue(result.getResponse().getContentAsString().contains("Maretta"));
    }

    @Test
    void getAddressById_addressFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/users/address/" + address.getAddressId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Baker"));
    }

    @Test
    void getAddressById_addressNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/users/address/" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void getAddressByContact_addressFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/users/address?contactId=" + contact.getContactId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Madrid"));
    }

    @Test
    void getAddressByContact_addressNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/users/address?contactId=" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void updateAddress() throws Exception {
        AddressDTO address = new AddressDTO("Baker Str.", "33", "London",
                "United Kingdom", contact.getContactId());
        String body = objectMapper.writeValueAsString(address);
        MvcResult result = mockMvc.perform(put("/api/users/address/update/" + address1.getAddressId()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("33"));
    }

    @Test
    void addAddress() throws Exception {
        int numberOfAddresses = addressRepository.findAll().size();
        AddressDTO address = new AddressDTO("Nowa", "45", "Warsaw",
                "Poland", contact.getContactId());
        String body = objectMapper.writeValueAsString(address);
        MvcResult result = mockMvc.perform(post("/api/users/address/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        int numberOfAddressesAfter = addressRepository.findAll().size();
        assertTrue(result.getResponse().getContentAsString().contains("Warsaw"));
        assertEquals(++numberOfAddresses, numberOfAddressesAfter);
    }

    @Test
    void deleteAddress() throws Exception {
        int numberOfAddresses = addressRepository.findAll().size();
        MvcResult result = mockMvc.perform(
                delete("/api/users/address/delete/" + address.getAddressId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        int numberOfAddressesAfterDelete = addressRepository.findAll().size();
        assertEquals(--numberOfAddresses, numberOfAddressesAfterDelete);
    }
}
