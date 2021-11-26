package com.sonas.cvservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonas.cvservice.controller.dto.TechnologyDTO;
import com.sonas.cvservice.dao.Cv;
import com.sonas.cvservice.dao.Technology;
import com.sonas.cvservice.enums.CvType;
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

import java.util.ArrayList;
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

    private List<Technology> techs = new ArrayList<>();

    private Cv cv;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        cv = new Cv(2L, CvType.COLOR, 2L, 3L, "eating", "Scrum Master", "Regular", "This is me");
        cvRepository.save(cv);
        tech = new Technology("JavaScript", cv.getCvId());
        tech1 = new Technology("Python", cv.getCvId());
        techs.addAll(List.of(tech, tech1));
        cv.setTechnology(techs);
        cvRepository.save(cv);
        technologyRepository.saveAll(List.of(tech, tech1));
    }

    @AfterEach
    public  void tearDown() {
        technologyRepository.deleteAll();
        cvRepository.deleteAll();
    }

    @Test
    void getTech() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/curriculums/tech/get"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("JavaScript"));
        assertTrue(result.getResponse().getContentAsString().contains("Python"));
    }

    @Test
    void getTechById_techFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/curriculums/tech/" + tech1.getTechId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Python"));
    }

    @Test
    void getTechById_techNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/curriculums/tech/" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void getTechByCv_techFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/curriculums/tech?cvId=" + cv.getCvId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("JavaScript"));
    }

    @Test
    void getTechByCv_techNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/curriculums/tech?cvId=" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void updateTech() throws Exception {
        TechnologyDTO techDTO = new TechnologyDTO("C#", cv.getCvId());
        String body = objectMapper.writeValueAsString(techDTO);
        MvcResult result = mockMvc.perform(put("/api/curriculums/tech/update/" + tech1.getTechId()).content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("C#"));
    }

    @Test
    void addTech() throws Exception {
        int numberOfTech = technologyRepository.findAll().size();
        TechnologyDTO techDTO = new TechnologyDTO("SQL", cv.getCvId());
        String body = objectMapper.writeValueAsString(techDTO);
        MvcResult result = mockMvc.perform(post("/api/curriculums/tech/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        int numberOfTechAfter = technologyRepository.findAll().size();
        assertTrue(result.getResponse().getContentAsString().contains("SQL"));
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
