package com.sonas.cvservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonas.cvservice.dao.Cv;
import com.sonas.cvservice.dao.Experience;
import com.sonas.cvservice.enums.CvType;
import com.sonas.cvservice.repository.CvRepository;
import com.sonas.cvservice.repository.ExperienceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
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

    private List<Experience> exps = new ArrayList<>();

    private Cv cv;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        cv = new Cv(1L, CvType.COLOR, 2L, 3L, "whatever", "QA Specialist",
                "Senior", "dddddddddddddddddddddddddddddddd");
        cvRepository.save(cv);
        exp = new Experience("Teacher", "Marteo School", LocalDate.of(1999,9,1),
                             LocalDate.of(2005, 6, 23),
                              "teaching", cv.getCvId());
        exp1 = new Experience("QA", "BioBot", LocalDate.of(2018, 1, 1),
                              LocalDate.of(2020, 1, 1),
                               "integration tests", cv.getCvId());
        exps.addAll(List.of(exp, exp1));
        cv.setExperience(exps);
        cvRepository.save(cv);
        experienceRepository.saveAll(List.of(exp, exp1));
    }

    @AfterEach
    public  void tearDown() {
        experienceRepository.deleteAll();
        cvRepository.deleteAll();
    }

    @Test
    void getExp() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/curriculums/exp/get"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Teacher"));
        assertTrue(result.getResponse().getContentAsString().contains("QA"));
    }

    @Test
    void getExpById_expFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/curriculums/exp/" + exp1.getExperienceId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("BioBot"));
    }

    @Test
    void getExpById_expNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/curriculums/exp/" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void getExpByCv_expNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/curriculums/exp?cvId=" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void getExpByCv_expFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/curriculums/exp?cvId=" + cv.getCvId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Teacher"));
    }

//    @Test
//    void updateExp() throws Exception {
//        ExperienceDTO expDTO = new ExperienceDTO("Teacher",
//                                                "Martino School",
//                                                         LocalDate.of(1999,9,1),
//                                                         LocalDate.of(2005, 6, 23),
//                                                  "teaching",
//                                                         cv.getCvId()
//        );
//        String body = objectMapper.writeValueAsString(expDTO);
//        MvcResult result = mockMvc.perform(put("/api/curriculums/exp/update/" + exp1.getExperienceId()).content(body)
//                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent()).andReturn();
//        assertTrue(result.getResponse().getContentAsString().contains("Martino"));
//    }

//    @Test
//    void addExp() throws Exception {
//        int numberOfExp = experienceRepository.findAll().size();
//        ExperienceDTO expDTO = new ExperienceDTO("Security Specialist",
//                                                 "Google",
//                                                 LocalDate.of(1980, 2, 13),
//                                                 LocalDate.of(1998, 1, 4),
//                                                 "scanning threaths",
//                                                 cv.getCvId()
//        );
//        String body = objectMapper.writeValueAsString(expDTO);
//        MvcResult result = mockMvc.perform(post("/api/curriculums/exp/new").content(body)
//                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
//        int numberOfExpAfter = experienceRepository.findAll().size();
//        assertTrue(result.getResponse().getContentAsString().contains("Security"));
//        assertEquals(++numberOfExp, numberOfExpAfter);
//    }

    @Test
    void deleteExp() throws Exception {
        int numberOfExp = experienceRepository.findAll().size();
        MvcResult result = mockMvc.perform(
                delete("/api/curriculums/exp/delete/" + exp1.getExperienceId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        int numberOfExpAfterDelete = experienceRepository.findAll().size();
        assertEquals(--numberOfExp, numberOfExpAfterDelete);
    }
}
