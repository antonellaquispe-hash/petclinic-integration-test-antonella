package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.services.VetSpecialtyService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class VetSpecialtyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    @Test
    public void testAssignSpecialty() throws Exception {
        VetSpecialtyDTO dto = new VetSpecialtyDTO();
        dto.setVetId(1);
        dto.setSpecId(1);
        mockMvc.perform(post("/vet-specialties")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"vetId\":1,\"specId\":1}"))
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