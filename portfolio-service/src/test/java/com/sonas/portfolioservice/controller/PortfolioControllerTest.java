package com.sonas.portfolioservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonas.portfolioservice.controller.dto.PortfolioDTO;
import com.sonas.portfolioservice.dao.Portfolio;
import com.sonas.portfolioservice.enums.PortfolioType;
import com.sonas.portfolioservice.repository.PortfolioRepository;
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
public class PortfolioControllerTest {

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Portfolio portfolio1;
    private Portfolio portfolio2;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        portfolio1 = new Portfolio(1, PortfolioType.DARK, "myPhoto.jpg","Once upon a time");
        portfolio2 = new Portfolio(2, PortfolioType.LIGHT, "portrait.png","To be or not to be");
        portfolioRepository.saveAll(List.of(portfolio1, portfolio2));
    }

    @AfterEach
    public  void tearDown() {
        portfolioRepository.deleteAll();
    }

    @Test
    void getPortfolios() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/portfolios")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Once upon a time"));
        assertTrue(result.getResponse().getContentAsString().contains("To be or not to be"));
    }

    @Test
    void getPortfolioById_portfolioFound() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/portfolios/" + portfolio1.getPortfolioId())
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Once upon a time"));
    }

    @Test
    void getPortfolioById_portfolioNotFound() throws Exception {
        mockMvc.perform(
                        get("/api/portfolios/" + 0)
                ).andDo(print()).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

//    @Test
//    void getPortfolioByUser_portfolioFound() throws Exception {
//        mockMvc.perform(
//                        get("/api/portfolios/" + 0)
//                ).andDo(print()).andExpect(status().isNotFound())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
//    }

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
