package com.supung.orphanage.controller;

import com.supung.orphanage.model.dto.OrphanageInputDTO;
import com.supung.orphanage.model.dto.OrphanageOutputDTO;
import com.supung.orphanage.service.OrphanageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class OrphanageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock private OrphanageService orphanageService;

    private OrphanageOutputDTO orphanageOutputDTO;

    private static final String ROOT_URL = "/api/v1/orphanages";
    private static final String ID_URL = ROOT_URL + "/1";

    @BeforeEach
    void setUp() {
        orphanageOutputDTO = OrphanageOutputDTO.builder()
                .id(1l).isDeleted(false).name("test_Orphanage")
                .district("test").city("test").build();
    }

    @Test
    void addTest() throws Exception {
        OrphanageInputDTO orphanageInputDTO = OrphanageInputDTO.builder()
                .name("test_Orphanage").district("test").city("test").build();
        when(orphanageService.save(orphanageInputDTO)).thenReturn(orphanageOutputDTO);

        mockMvc.perform(post(ROOT_URL).contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\" : \"test_Orphanage\",\n" +
                        "    \"district\" : \"test\",\n" +
                        "    \"city\" : \"test\"\n" +
                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    void getAll() {
    }

    @Test
    void getByIdTest() throws Exception {
        when(orphanageService.findById(1)).thenReturn(orphanageOutputDTO);

        mockMvc.perform(get(ID_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(orphanageOutputDTO.getName()));
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
