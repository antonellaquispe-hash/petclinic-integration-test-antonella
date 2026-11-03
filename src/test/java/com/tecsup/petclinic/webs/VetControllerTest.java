package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.entities.Vet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
class VetControllerTest {
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@Test
	void testFindAllVets() throws Exception {
		mockMvc.perform(get("/vets"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").exists());
	}

	@Test
	void testFindVetOK() throws Exception {
		mockMvc.perform(get("/vets/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.lastName", is("Carter")));
	}

	@Test
	void testFindVetKO() throws Exception {
		mockMvc.perform(get("/vets/666")).andExpect(status().isNotFound());
	}

	@Test
	void testCreateVet() throws Exception {
		Vet vet = new Vet();
		vet.setFirstName("Ana");
		vet.setLastName("Lopez");
		vet.setEmail("ana.lopez@example.test");
		vet.setPhone("5550101");
		vet.setActive(true);

		mockMvc.perform(post("/vets").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(vet)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.lastName", is("Lopez")));
	}
}
