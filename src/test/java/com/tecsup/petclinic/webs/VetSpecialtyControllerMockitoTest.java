package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.services.VetSpecialtyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VetSpecialtyControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VetSpecialtyService vetSpecialtyServiceMock;

    @Test
    public void testAssignSpecialty() throws Exception {
        VetSpecialtyDTO dto = new VetSpecialtyDTO();
        dto.setVetId(1);
        dto.setSpecialtyId(1);

        Mockito.when(vetSpecialtyServiceMock.assignSpecialty(Mockito.any(VetSpecialtyDTO.class)))
                .thenReturn(dto);

        mockMvc.perform(post("/vet-specialties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"vetId\":1,\"specialtyId\":1}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAssignDuplicateShouldFail() throws Exception {
        VetSpecialtyDTO dto = new VetSpecialtyDTO();
        dto.setVetId(1);
        dto.setSpecialtyId(1);

        // Primera llamada OK, segunda lanza excepción
        Mockito.when(vetSpecialtyServiceMock.assignSpecialty(Mockito.any(VetSpecialtyDTO.class)))
                .thenReturn(dto)
                .thenThrow(new RuntimeException("Duplicate assignment"));

        mockMvc.perform(post("/vet-specialties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"vetId\":1,\"specialtyId\":1}"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/vet-specialties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"vetId\":1,\"specialtyId\":1}"))
                .andExpect(status().isConflict());
    }
}