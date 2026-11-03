package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.services.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TypeControllerTest {

    @Autowired
    private TypeService typeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void testFindById() throws Exception {
        mockMvc.perform(get("/types/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("cat")));
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        mockMvc.perform(get("/types/666"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreate() throws Exception {
        TypeDTO newType = new TypeDTO();
        newType.setName("fish");
        mockMvc.perform(post("/types")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"fish\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("fish")));
    }

    @Test
    public void testDelete() throws Exception {
        // First create a type
        var mvcActions = mockMvc.perform(post("/types")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"testtype\"}"))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = Integer.parseInt(response.trim());

        // Then delete
        mockMvc.perform(delete("/types/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete("/types/1000"))
                .andExpect(status().isNotFound());
    }
}