package com.sonas.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonas.userservice.controller.dto.UserDTO;
import com.sonas.userservice.dao.User;
import com.sonas.userservice.enums.UserType;
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
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private User user1;

    private User user2;

    private User user3;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user1 = new User("maryJane@gmail.com", "pass", "Mary",
                "Jane", "maryJ",UserType.BASIC);
        user2 = new User("peterParker@gmail.com", "pass1", "Peter", "Parker",
                "peterP", UserType.PREMIUM);
        user3 = new User("admin@gmail.com", "admin", UserType.ADMIN);
        userRepository.saveAll(List.of(user1, user2, user3));
    }

    @AfterEach
    public  void tearDown() {
        userRepository.deleteAll();
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
                                      "BASIC"
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
                                      "PREMIUM"
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
