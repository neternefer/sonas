package com.sonas.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonas.userservice.controller.dto.ContactDTO;
import com.sonas.userservice.controller.dto.SocialDTO;
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
public class SocialControllerTest {

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

    private Social social1;

    private List<Address> addresses = new ArrayList<>();

    private List<Social> socialLinks = new ArrayList<>();

    private Contact contact;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        contact = new Contact("111222333");
        contactRepository.save(contact);
        address = new Address("Baker Str.", "11", "London",
                "United Kingdom", contact.getContactId());
        address1 = new Address("Maretta", "56", "Madrid",
                "Spain", contact.getContactId());
        addresses.addAll(List.of(address, address1));
        social = new Social("Facebook", new URL("https://www.facebook.com/Test/"),
                contact.getContactId());
        social1 = new Social("Twitter", new URL("https://twitter.com/Test/"),
                contact.getContactId());
        socialLinks.addAll(List.of(social, social1));
        contact.setAddress(addresses);
        contact.setSocial(socialLinks);
        contactRepository.save(contact);
        addressRepository.saveAll(List.of(address, address1));
        socialRepository.saveAll(List.of(social, social1));
    }

    @AfterEach
    public  void tearDown() {
        addressRepository.deleteAll();
        socialRepository.deleteAll();
        contactRepository.deleteAll();
    }

    @Test
    void getSocialLinks() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users/social/get"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Twitter"));
        assertTrue(result.getResponse().getContentAsString().contains("Facebook"));
    }

    @Test
    void getSocialLinksById_linksFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/users/social/" + social.getSocialId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("https://www.facebook.com/Test/"));
    }

    @Test
    void getSocialLinksById_linksNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/users/social/" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void getSocialLinksByContact_linksFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/users/social?contactId=" + contact.getContactId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Facebook"));
    }

    @Test
    void getSocialLinksByContact_linksNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/users/social?contactId=" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void updateSocial() throws Exception {
        SocialDTO social = new SocialDTO("Twitter", "https://twitter.com/Test1/",
                contact.getContactId());
        String body = objectMapper.writeValueAsString(social);
        MvcResult result = mockMvc.perform(put("/api/users/social/update/" + social1.getSocialId()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("https://twitter.com/Test1/"));
    }

    @Test
    void addSocial() throws Exception {
        int numberOfSocialLinks = socialRepository.findAll().size();
        SocialDTO social = new SocialDTO("Github", "https://github.com/test",
                contact.getContactId());
        String body = objectMapper.writeValueAsString(social);
        MvcResult result = mockMvc.perform(post("/api/users/social/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        int numberOfSocialLinksAfter = socialRepository.findAll().size();
        assertTrue(result.getResponse().getContentAsString().contains("Github"));
        assertTrue(result.getResponse().getContentAsString().contains("https://github.com/test"));
        assertEquals(++numberOfSocialLinks, numberOfSocialLinksAfter);
    }

    @Test
    void deleteSocial() throws Exception {
        int numberOfSocialLinks = socialRepository.findAll().size();
        MvcResult result = mockMvc.perform(
                delete("/api/users/social/delete/" + social.getSocialId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        int numberOfSocialLinksAfterDelete = socialRepository.findAll().size();
        assertEquals(--numberOfSocialLinks, numberOfSocialLinksAfterDelete);
    }
}
