package com.sonas.cvservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonas.cvservice.controller.dto.ExperienceDTO;
import com.sonas.cvservice.dao.Cv;
import com.sonas.cvservice.dao.Experience;
import com.sonas.cvservice.repository.CvRepository;
import com.sonas.cvservice.repository.ExperienceRepository;
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
public class ExperienceControllerTest {

    @Autowired
    ExperienceRepository experienceRepository;

    @Autowired
    CvRepository cvRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Experience exp;

    private Experience exp1;

    private Cv cv;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        exp = new Experience();
        exp1 = new Experience();
        cv = new Cv();
        experienceRepository.saveAll(List.of(exp, exp1));
        cvRepository.save(cv);
    }

    @AfterEach
    public  void tearDown() {
       experienceRepository.deleteAll();
    }

    @Test
    void getExp() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/curriculums/exp"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }

    @Test
    void getExpById_expFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/curriculums/exp" + exp1.getExperienceId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }

    @Test
    void getExpById_expNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/curriculums/exp" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void getExpByCv_expNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/curriculums/exp" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void getExpByCv_expFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/curriculums/exp" + cv.getCvId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }

    @Test
    void updateExp() throws Exception {
        ExperienceDTO expDTO = new ExperienceDTO(

        );
        String body = objectMapper.writeValueAsString(expDTO);
        MvcResult result = mockMvc.perform(put("/api/curriculums/exp/update/" + exp1.getExperienceId()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }

    @Test
    void addExp() throws Exception {
        int numberOfExp = experienceRepository.findAll().size();
        ExperienceDTO expDTO = new ExperienceDTO();
        String body = objectMapper.writeValueAsString(expDTO);
        MvcResult result = mockMvc.perform(post("/api/curriculums/exp/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        int numberOfExpAfter = experienceRepository.findAll().size();
        assertTrue(result.getResponse().getContentAsString().contains(""));
        assertEquals(++numberOfExp, numberOfExpAfter);
    }

    @Test
    void deleteExp() throws Exception {
        int numberOfExp = experienceRepository.findAll().size();
        MvcResult result = mockMvc.perform(
                delete("/api/curriculums/experience/delete/" + exp1.getExperienceId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        int numberOfExpAfterDelete = experienceRepository.findAll().size();
        assertEquals(--numberOfExp, numberOfExpAfterDelete);
    }
}
