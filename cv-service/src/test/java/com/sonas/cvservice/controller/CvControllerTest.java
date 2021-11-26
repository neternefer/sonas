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
        cv1 = new Cv(1L, CvType.SIMPLE, 2L, 1L, "books, running", "Team Leader", "Mid","It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old.");
        cv2 = new Cv(2L, CvType.COLOR, 1L, 2L, "swimming, movies", "QA Specialist", "Junior", "This book is a treatise on the theory of ethics, very popular during the Renaissance.");
        cvRepository.saveAll(List.of(cv1, cv2));
    }

    @AfterEach
    public  void tearDown() {
        cvRepository.deleteAll();
    }

    @Test
    void getCvs() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/curriculums")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old."));
        assertTrue(result.getResponse().getContentAsString().contains("swimming"));
    }

    @Test
    void getCvById_cvFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/curriculums/" + cv1.getCvId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Team Leader"));
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
        CvDTO cvDTO = new CvDTO(3, CvType.SIMPLE.toString(), 3L, 3L, "nothing really", "Web Developer", "Senior", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer volutpat quis turpis at fermentum. Nulla convallis ullamcorper dolor, quis finibus dolor porta in.");
        String body = objectMapper.writeValueAsString(cvDTO);
        MvcResult result = mockMvc.perform(post("/api/curriculums/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        int numberOfCvsAfter = cvRepository.findAll().size();
        assertTrue(result.getResponse().getContentAsString().contains("nothing really"));
        assertEquals(++numberOfCvs, numberOfCvsAfter);
    }

    @Test
    void updateCv() throws Exception {
        CvDTO cvDTO = new CvDTO(2L,
                                      CvType.COLOR.toString(),
                              1L,
                               2L,
                                "swimming, movies",
                               "QA Specialist",
                              "Mid",
                                 "This book is a treatise on the theory of ethics, very popular during the Renaissance."

        );
        String body = objectMapper.writeValueAsString(cvDTO);
        MvcResult result = mockMvc.perform(put("/api/curriculums/update/" + cv1.getCvId()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Mid"));
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
