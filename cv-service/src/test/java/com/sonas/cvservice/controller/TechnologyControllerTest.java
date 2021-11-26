package com.sonas.cvservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonas.cvservice.controller.dto.TechnologyDTO;
import com.sonas.cvservice.dao.Cv;
import com.sonas.cvservice.dao.Technology;
import com.sonas.cvservice.repository.CvRepository;
import com.sonas.cvservice.repository.TechnologyRepository;
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
public class TechnologyControllerTest {

    @Autowired
    TechnologyRepository technologyRepository;

    @Autowired
    CvRepository cvRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Technology tech;

    private Technology tech1;

    private Cv cv;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        tech = new Technology();
        tech1 = new Technology();
        cv = new Cv();
        technologyRepository.saveAll(List.of(tech, tech1));
        cvRepository.save(cv);
    }

    @AfterEach
    public  void tearDown() {
        technologyRepository.deleteAll();
    }

    @Test
    void getTech() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/curriculums/tech"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }

    @Test
    void getTechById_techFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/curriculums/tech" + tech1.getTechId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }

    @Test
    void getTechById_techNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/curriculums/tech" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void getTechByCv_techFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/curriculums/tech" + cv.getCvId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }

    @Test
    void getTechByCv_techNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/curriculums/tech" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void updateTech() throws Exception {
        TechnologyDTO techDTO = new TechnologyDTO(

        );
        String body = objectMapper.writeValueAsString(techDTO);
        MvcResult result = mockMvc.perform(put("/api/curriculums/tech/update/" + tech1.getTechId()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(""));
    }

    @Test
    void addTech() throws Exception {
        int numberOfTech = technologyRepository.findAll().size();
        TechnologyDTO techDTO = new TechnologyDTO();
        String body = objectMapper.writeValueAsString(techDTO);
        MvcResult result = mockMvc.perform(post("/api/curriculums/exp/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        int numberOfTechAfter = technologyRepository.findAll().size();
        assertTrue(result.getResponse().getContentAsString().contains(""));
        assertEquals(++numberOfTech, numberOfTechAfter);
    }

    @Test
    void deleteTech() throws Exception {
        int numberOfTech = technologyRepository.findAll().size();
        MvcResult result = mockMvc.perform(
                delete("/api/curriculums/tech/delete/" + tech1.getTechId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        int numberOfTechAfterDelete = technologyRepository.findAll().size();
        assertEquals(--numberOfTech, numberOfTechAfterDelete);
    }
}
