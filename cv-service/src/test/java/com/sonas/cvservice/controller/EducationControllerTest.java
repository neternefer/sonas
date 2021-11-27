package com.sonas.cvservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonas.cvservice.controller.dto.EducationDTO;
import com.sonas.cvservice.dao.Cv;
import com.sonas.cvservice.dao.Education;
import com.sonas.cvservice.enums.CvType;
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

import java.time.LocalDate;
import java.util.ArrayList;
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

    private List<Education> schools = new ArrayList<>();

    private Cv cv;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        cv = new Cv(2L, CvType.COLOR, 2L, 3L, "eating", "Scrum Master", "Regular", "This is me");
        cvRepository.save(cv);
        edu = new Education("MIT", LocalDate.of(1990, 12, 21),
                LocalDate.of(1994, 5, 12), "Physics", "MSc", cv.getCvId());
        edu1 = new Education("Yale", LocalDate.of(2017, 9, 1),
                LocalDate.of(2021, 5, 1), "History", "MA", 2L);
        schools.add(edu);
        cv.setEducation(schools);
        cvRepository.save(cv);
        educationRepository.saveAll(List.of(edu, edu1));
    }

    @AfterEach
    public  void tearDown() {
        educationRepository.deleteAll();
        cvRepository.deleteAll();
    }

    @Test
    void getSchools() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/curriculums/education/get"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Physics"));
        assertTrue(result.getResponse().getContentAsString().contains("History"));
    }

    @Test
    void getSchoolById_schoolFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/curriculums/education/" + edu1.getEducationId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Yale"));
    }

    @Test
    void getSchoolById_schoolNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/curriculums/education/" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void getSchoolByCv_schoolFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/curriculums/education?cvId=" + cv.getCvId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Physics"));
    }

    @Test
    void getSchoolByCv_schoolNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/curriculums/education?cvId=" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

//    @Test
//    void updateSchool() throws Exception {
//        EducationDTO schoolDTO = new EducationDTO("MIT", LocalDate.of(1990, 11, 21),
//                LocalDate.of(1994, 5, 12), "Physics", "MSc", cv.getCvId());
//        String body = objectMapper.writeValueAsString(schoolDTO);
//        MvcResult result = mockMvc.perform(put("/api/curriculums/education/update/" + edu1.getEducationId()).content(body)
//                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent()).andReturn();
//        assertTrue(result.getResponse().getContentAsString().contains("11"));
//    }

//    @Test
//    void addSchool() throws Exception {
//        int numberOfSchools = educationRepository.findAll().size();
//        EducationDTO schoolDTO = new EducationDTO("Calteh",
//                                                  LocalDate.of(2000, 10, 1),
//                                                  LocalDate.of(2003, 5, 10),
//                                                  "CS",
//                                                  "PhD",
//                                                  cv.getCvId());
//        String body = objectMapper.writeValueAsString(schoolDTO);
//        MvcResult result = mockMvc.perform(post("/api/curriculums/education/new").content(body)
//                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
//        int numberOfSchoolsAfter = educationRepository.findAll().size();
//        assertTrue(result.getResponse().getContentAsString().contains("Calteh"));
//        assertEquals(++numberOfSchools, numberOfSchoolsAfter);
//    }

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
