package com.sonas.portfolioservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonas.portfolioservice.controller.dto.PortfolioDTO;
import com.sonas.portfolioservice.dao.MinTechnology;
import com.sonas.portfolioservice.dao.Portfolio;
import com.sonas.portfolioservice.enums.PortfolioType;
import com.sonas.portfolioservice.repository.MinTechRepository;
import com.sonas.portfolioservice.repository.PortfolioRepository;
import com.sonas.portfolioservice.service.MinTechnologyService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class MinTechControllerTest {

    @Autowired
    MinTechnologyService minTechnologyService;

    @Autowired
    MinTechRepository minTechRepository;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MinTechnology tech;

    private MinTechnology tech1;

    private List<MinTechnology> techs = new ArrayList<>();

    private Portfolio portfolio1;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        portfolio1 = new Portfolio(1, PortfolioType.DARK, "myPhoto.jpg","Once upon a time");
        portfolioRepository.save(portfolio1);
        tech = new MinTechnology("Py", portfolio1.getPortfolioId());
        tech1 = new MinTechnology("JS", portfolio1.getPortfolioId());
        techs.addAll(List.of(tech, tech1));
        portfolio1.setMinTech(techs);
        portfolioRepository.save(portfolio1);
        minTechRepository.saveAll(List.of(tech, tech1));
    }

    @AfterEach
    public  void tearDown() {
        portfolioRepository.deleteAll();
        minTechRepository.deleteAll();
    }

    @Test
    void getTech() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/portfolios/mintech/get")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Py"));
        assertTrue(result.getResponse().getContentAsString().contains("JS"));
    }

    @Test
    void getTechById_techFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/portfolios/mintech/" + tech.getMinTechId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Py"));
    }

    @Test
    void getTechById_techNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/portfolios/mintech/" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void getTechByPortfolio_techFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/portfolios/mintech?portfolioId=" + portfolio1.getPortfolioId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("JS"));
    }

    @Test
    void getTechByPortfolio_techNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/portfolios/mintech?portfolioId=" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    void addPortfolio() throws Exception {
        int numberOfPortfolios = portfolioRepository.findAll().size();
        PortfolioDTO portfolioDTO = new PortfolioDTO(3,
                PortfolioType.DARK.toString(),
                "pic1.jpg",
                "Hakunamatata");
        String body = objectMapper.writeValueAsString(portfolioDTO);
        MvcResult result = mockMvc.perform(post("/api/portfolios/new").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andReturn();
        int numberOfPortfoliosAfter = portfolioRepository.findAll().size();
        assertEquals(++numberOfPortfolios, numberOfPortfoliosAfter);
        assertTrue(result.getResponse().getContentAsString().contains("Hakunamatata"));
    }

    @Test
    void deletePortfolio() throws Exception {
        int numberOfPortfolios = portfolioRepository.findAll().size();
        MvcResult result = mockMvc.perform(
                delete("/api/portfolios/delete/" + portfolio1.getPortfolioId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        int numberOfPortfoliosAfterDelete = portfolioRepository.findAll().size();
        assertEquals(--numberOfPortfolios, numberOfPortfoliosAfterDelete);
    }
}
