package com.tecsup.petclinic.webs;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TypeControllerTest {

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
        mockMvc.perform(post("/types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"fish\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("fish")));
    }

    @Test
    public void testDelete() throws Exception {
        String response = mockMvc.perform(post("/types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"testtype\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Integer id = JsonPath.read(response, "$.id");

        mockMvc.perform(delete("/types/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete("/types/1000"))
                .andExpect(status().isNotFound());
    }
}