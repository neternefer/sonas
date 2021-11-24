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

import static org.junit.jupiter.api.Assertions.assertTrue;
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
        user1 = new User("maryJane@gmail.com", "pass", UserType.BASIC);
        user2 = new User("peterParker@gmail.com", "pass1", UserType.PREMIUM);
        user3 = new User("admin@gmail.com", "admin", UserType.ADMIN);
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @AfterEach
    public  void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void getUsers() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/users")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }

    @Test
    void getUserById() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/users/" + user1.getUserId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }

    @Test
    void addUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        String body = objectMapper.writeValueAsString(userDTO);
        MvcResult result = mockMvc.perform(post("/api/users/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }

    @Test
    void updateUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        String body = objectMapper.writeValueAsString(userDTO);
        MvcResult result = mockMvc.perform(put("/api/users/update/" + user1.getUserId()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }

    @Test
    void deleteUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        String body = objectMapper.writeValueAsString(userDTO);
        MvcResult result = mockMvc.perform(delete("/api/users/delete/" + user1.getUserId()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }
}
