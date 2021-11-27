package com.sonas.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonas.userservice.controller.dto.UserDTO;
import com.sonas.userservice.dao.Address;
import com.sonas.userservice.dao.Contact;
import com.sonas.userservice.dao.Social;
import com.sonas.userservice.dao.User;
import com.sonas.userservice.enums.UserType;
import com.sonas.userservice.repository.AddressRepository;
import com.sonas.userservice.repository.ContactRepository;
import com.sonas.userservice.repository.SocialRepository;
import com.sonas.userservice.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    UserRepository userRepository;

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

    private User user1;

    private User user2;

    private User user3;

    private Address address;

    private Address address1;

    private Social social;

    private List<Address> addresses = new ArrayList<>();

    private List<Social> socialLinks = new ArrayList<>();

    private Contact contact;

    private Contact contact1;

    private Contact contact2;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        contact = new Contact("111222333");
        contact1 = new Contact("999888777");
        contact2 = new Contact("333333333");
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
        contactRepository.saveAll(List.of(contact, contact1, contact2));
        addressRepository.saveAll(List.of(address, address1));
        socialRepository.save(social);
        user1 = new User("maryJane@gmail.com", "pass", "Mary",
                "Jane", "maryJ",UserType.BASIC, contact.getContactId());
        user2 = new User("peterParker@gmail.com", "pass1", "Peter", "Parker",
                "peterP", UserType.PREMIUM, contact1.getContactId());
        user3 = new User("admin@gmail.com", "admin", UserType.ADMIN);
        userRepository.saveAll(List.of(user1, user2, user3));
    }

    @AfterEach
    public  void tearDown() {
        addressRepository.deleteAll();
        socialRepository.deleteAll();
        contactRepository.deleteAll();
    }

    @Test
    void getUsers() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("maryJane@gmail.com"));
        assertTrue(result.getResponse().getContentAsString().contains("peterParker@gmail.com"));
    }

    @Test
    void getUserById_userFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/users/" + user1.getUserId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("maryJane@gmail.com"));
    }

    @Test
    void getUserById_userNotFound() throws Exception {
        mockMvc.perform(
                get("/api/users/" + 0)
        ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void getUserByType_userFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/users?userType=" + user1.getUserType())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("maryJane@gmail.com"));
    }

    @Test
    void getUserByType_userNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/users?userType=" + "GOLD")
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void addUser() throws Exception {
        UserDTO userDTO = new UserDTO("johnDoe@gmailcom",
                                      "pass2",
                                      "John",
                                      "Doe",
                                      "johnD",
                                      "BASIC",
                                              contact2.getContactId()
        );
        String body = objectMapper.writeValueAsString(userDTO);
        MvcResult result = mockMvc.perform(post("/api/users/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("johnDoe@gmailcom"));
    }

    @Test
    void updateUser() throws Exception {
        UserDTO userDTO = new UserDTO("maryJane@gmail.com",
                                      "pass",
                                      "Mary",
                                      "Jane",
                                      "maryJ",
                                      "PREMIUM",
                                              contact.getContactId()
        );
        String body = objectMapper.writeValueAsString(userDTO);
        MvcResult result = mockMvc.perform(put("/api/users/update/" + user1.getUserId()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("PREMIUM"));
    }

    @Test
    void deleteUser() throws Exception {
        int numberOfUsers = userRepository.findAll().size();
        MvcResult result = mockMvc.perform(
                delete("/api/users/delete/" + user1.getUserId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        int numberOfUsersAfterDelete = userRepository.findAll().size();
        assertEquals(--numberOfUsers, numberOfUsersAfterDelete);
    }
}
