package com.sonas.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonas.userservice.controller.dto.AddressDTO;
import com.sonas.userservice.controller.dto.ContactDTO;
import com.sonas.userservice.dao.Address;
import com.sonas.userservice.dao.Contact;
import com.sonas.userservice.dao.Social;
import com.sonas.userservice.repository.AddressRepository;
import com.sonas.userservice.repository.ContactRepository;
import com.sonas.userservice.repository.SocialRepository;
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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ContactControllerTest {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    SocialRepository socialRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Address address;

    private Address address1;

    private Social social;

    private List<Address> addresses = new ArrayList<>();

    private List<Social> socialLinks = new ArrayList<>();

    private Contact contact;

    private Contact contact1;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        contact = new Contact("111222333");
        contact1 = new Contact("999888777");
        contactRepository.save(contact);
        address = new Address("Baker Str.", "11", "London",
                "United Kingdom", contact.getContactId());
        address1 = new Address("Maretta", "56", "Madrid",
                "Spain", contact.getContactId());
        addresses.addAll(List.of(address, address1));
        social = new Social("Facebook", new URL("https://www.facebook.com/Test/"),
                contact.getContactId());
        socialLinks.add(social);
        contact.setAddress(addresses);
        contact1.setSocial(socialLinks);
        contactRepository.save(contact);
        addressRepository.saveAll(List.of(address, address1));
        socialRepository.save(social);
    }

    @AfterEach
    public  void tearDown() {
        addressRepository.deleteAll();
        socialRepository.deleteAll();
        contactRepository.deleteAll();
    }

    @Test
    void getContacts() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users/contact"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("111222333"));
        assertTrue(result.getResponse().getContentAsString().contains("Facebook"));
    }

    @Test
    void getContactById_contactFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/users/contact/" + contact.getContactId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("London"));
    }

    @Test
    void getContactById_contactNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/users/contact/" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void updateContact() throws Exception {
        ContactDTO contact = new ContactDTO("666666666");
        String body = objectMapper.writeValueAsString(contact);
        MvcResult result = mockMvc.perform(put("/api/users/contact/update/" + contact1.getContactId()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("666666666"));
    }

    @Test
    void addContact() throws Exception {
        int numberOfContacts = contactRepository.findAll().size();
        ContactDTO contact = new ContactDTO("000999000", socialLinks, addresses);
        String body = objectMapper.writeValueAsString(contact);
        MvcResult result = mockMvc.perform(post("/api/users/contact/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        int numberOfContactsAfter = contactRepository.findAll().size();
        assertTrue(result.getResponse().getContentAsString().contains("000999000"));
        assertTrue(result.getResponse().getContentAsString().contains("Spain"));
        assertEquals(++numberOfContacts, numberOfContactsAfter);
    }

    @Test
    void deleteAddress() throws Exception {
        int numberOfContacts = contactRepository.findAll().size();
        MvcResult result = mockMvc.perform(
                delete("/api/users/contact/delete/" + contact.getContactId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        int numberOfContactsAfterDelete = addressRepository.findAll().size();
        assertEquals(--numberOfContacts, numberOfContactsAfterDelete);
    }
}
