package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.services.TypeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TypeControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TypeService typeServiceMock;

    @Test
    public void testFindAll() throws Exception {
        Mockito.when(typeServiceMock.findAll())
                .thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(get("/types"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindById() throws Exception {
        TypeDTO dto = new TypeDTO();
        dto.setId(1);
        dto.setName("cat");
        Mockito.when(typeServiceMock.findById(1)).thenReturn(dto);

        mockMvc.perform(get("/types/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("cat")));
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        Mockito.when(typeServiceMock.findById(666)).thenReturn(null);

        mockMvc.perform(get("/types/666"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreate() throws Exception {
        TypeDTO created = new TypeDTO();
        created.setName("fish");
        Mockito.when(typeServiceMock.create(Mockito.any(TypeDTO.class))).thenReturn(created);

        mockMvc.perform(post("/types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"fish\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("fish")));
    }
}