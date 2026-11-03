package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
class TypeControllerTest {
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@Test
	void testFindAll() throws Exception {
		mockMvc.perform(get("/types"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThan(0))));
	}

	@Test
	void testFindById() throws Exception {
		mockMvc.perform(get("/types/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("cat")));
	}

	@Test
	void testFindByIdNotFound() throws Exception {
		mockMvc.perform(get("/types/666")).andExpect(status().isNotFound());
	}

	@Test
	void testCreate() throws Exception {
		mockMvc.perform(post("/types").contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"fish\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is("fish")))
				.andExpect(jsonPath("$.id").isNumber());
	}

	@Test
	void testDelete() throws Exception {
		MvcResult created = mockMvc.perform(post("/types").contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"temporary-type\"}"))
				.andExpect(status().isCreated()).andReturn();
		JsonNode response = objectMapper.readTree(created.getResponse().getContentAsString());
		int id = response.get("id").asInt();

		mockMvc.perform(delete("/types/{id}", id)).andExpect(status().isOk());
		mockMvc.perform(get("/types/{id}", id)).andExpect(status().isNotFound());
	}

	@Test
	void testDeleteNotFound() throws Exception {
		mockMvc.perform(delete("/types/1000")).andExpect(status().isNotFound());
	}
}
