package com.tecsup.petclinic.webs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VetSpecialtyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAssignSpecialty() throws Exception {
        mockMvc.perform(post("/vet-specialties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"vetId\":1,\"specialtyId\":1}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testRemoveSpecialty() throws Exception {
        mockMvc.perform(delete("/vet-specialties/1/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testSetPrimary() throws Exception {
        mockMvc.perform(put("/vet-specialties/1/1/primary"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateExperience() throws Exception {
        mockMvc.perform(put("/vet-specialties/1/1/experience")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"yearsExperience\":5}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCertification() throws Exception {
        mockMvc.perform(put("/vet-specialties/1/1/certification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"certificationDate\":\"2023-01-01\"}"))
                .andExpect(status().isOk());
    }
}