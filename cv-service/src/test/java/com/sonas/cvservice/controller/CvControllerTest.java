package com.sonas.cvservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonas.cvservice.controller.dto.CvDTO;
import com.sonas.cvservice.dao.Cv;
import com.sonas.cvservice.enums.CvType;
import com.sonas.cvservice.repository.CvRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CvControllerTest {

    @Autowired
    CvRepository cvRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Cv cv1;
    private Cv cv2;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        cv1 = new Cv(1, CvType.SIMPLE);
        cv2 = new Cv(2, CvType.COLOR);
        cvRepository.saveAll(List.of(cv1, cv2));
    }

    @AfterEach
    public  void tearDown() {
        cvRepository.deleteAll();
    }

    @Test
    void getCvs() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/cvs")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("1"));
        assertTrue(result.getResponse().getContentAsString().contains("2"));
    }

    @Test
    void getCvById_cvFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/curriculums/" + cv1.getCvId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void getCvById_cvNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/curriculums/" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void getCvByUser_cvNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/curriculums/" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void addCv() throws Exception {
        int numberOfCvs = cvRepository.findAll().size();
        CvDTO cvDTO = new CvDTO(3, CvType.SIMPLE.toString());
        String body = objectMapper.writeValueAsString(cvDTO);
        MvcResult result = mockMvc.perform(post("/api/curriculums/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        int numberOfCvsAfter = cvRepository.findAll().size();
        assertTrue(result.getResponse().getContentAsString().contains("3"));
        assertEquals(numberOfCvs++, numberOfCvsAfter);
    }

    @Test
    void deleteCv() throws Exception {
        int numberOfCvs = cvRepository.findAll().size();
        MvcResult result = mockMvc.perform(
                delete("/api/curriculums/delete/" + cv1.getCvId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        int numberOfCvsAfterDelete = cvRepository.findAll().size();
        assertEquals(--numberOfCvs, numberOfCvsAfterDelete);
    }
}
