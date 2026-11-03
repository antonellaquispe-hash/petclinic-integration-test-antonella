package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.services.VetSpecialtyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class VetSpecialtyControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    private VetSpecialtyService vetSpecialtyServiceMock;

    @BeforeEach
    public void setUp() {
        vetSpecialtyServiceMock = Mockito.mock(VetSpecialtyService.class);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAssignSpecialty() throws Exception {
        VetSpecialtyDTO dto = new VetSpecialtyDTO();
        dto.setVetId(1);
        dto.setSpecId(1);
        Mockito.when(vetSpecialtyServiceMock.assignSpecialty(dto)).thenReturn(dto);
        mockMvc.perform(post("/vet-specialties")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"vetId\":1,\"specId\":1}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAssignDuplicateShouldFail() throws Exception {
        // First assignment
        VetSpecialtyDTO dto = new VetSpecialtyDTO();
        dto.setVetId(1);
        dto.setSpecId(1);
        Mockito.when(vetSpecialtyServiceMock.assignSpecialty(dto)).thenReturn(dto);

        mockMvc.perform(post("/vet-specialties")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"vetId\":1,\"specId\":1}"))
                .andExpect(status().isCreated());

        // Second assignment should conflict
        Mockito.when(vetSpecialtyServiceMock.assignSpecialty(dto))
                .thenThrow(new RuntimeException("Duplicate assignment"));

        mockMvc.perform(post("/vet-specialties")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"vetId\":1,\"specId\":1}"))
                .andExpect(status().isConflict());
    }
}