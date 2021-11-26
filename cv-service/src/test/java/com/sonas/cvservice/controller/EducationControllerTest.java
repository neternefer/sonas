package com.sonas.cvservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonas.cvservice.controller.dto.EducationDTO;
import com.sonas.cvservice.dao.Cv;
import com.sonas.cvservice.dao.Education;
import com.sonas.cvservice.repository.CvRepository;
import com.sonas.cvservice.repository.EducationRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class EducationControllerTest {

    @Autowired
    EducationRepository educationRepository;

    @Autowired
    CvRepository cvRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Education edu;

    private Education edu1;

    private Cv cv;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        edu = new Education();
        edu1 = new Education();
        cv = new Cv();
        educationRepository.saveAll(List.of(edu, edu1));
        cvRepository.save(cv);
    }

    @AfterEach
    public  void tearDown() {
        educationRepository.deleteAll();
    }

    @Test
    void getSchools() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/curriculums/education"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }

    @Test
    void getSchoolById_schoolFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/curriculums/education" + edu1.getEducationId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }

    @Test
    void getSchoolById_schoolNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/curriculums/education" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void getSchoolByCv_schoolFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/curriculums/education" + cv.getCvId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }

    @Test
    void getSchoolByCv_schoolNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/curriculums/education" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void updateSchool() throws Exception {
        EducationDTO schoolDTO = new EducationDTO(

        );
        String body = objectMapper.writeValueAsString(schoolDTO);
        MvcResult result = mockMvc.perform(put("/api/curriculums/education/update/" + edu1.getEducationId()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }

    @Test
    void addSchool() throws Exception {
        int numberOfSchools = educationRepository.findAll().size();
        EducationDTO schoolDTO = new EducationDTO();
        String body = objectMapper.writeValueAsString(schoolDTO);
        MvcResult result = mockMvc.perform(post("/api/curriculums/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        int numberOfSchoolsAfter = educationRepository.findAll().size();
        assertTrue(result.getResponse().getContentAsString().contains(""));
        assertEquals(++numberOfSchools, numberOfSchoolsAfter);
    }

    @Test
    void deleteSchool() throws Exception {
        int numberOfSchools = educationRepository.findAll().size();
        MvcResult result = mockMvc.perform(
                delete("/api/curriculums/education/delete/" + edu1.getEducationId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        int numberOfSchoolsAfterDelete = educationRepository.findAll().size();
        assertEquals(--numberOfSchools, numberOfSchoolsAfterDelete);
    }
}
