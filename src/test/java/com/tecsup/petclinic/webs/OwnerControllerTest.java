package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.entities.Owner;
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
class OwnerControllerTest {
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@Test
	void testFindAllOwners() throws Exception {
		mockMvc.perform(get("/owners"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").exists());
	}

	@Test
	void testFindOwnerOK() throws Exception {
		mockMvc.perform(get("/owners/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.lastName", is("Franklin")));
	}

	@Test
	void testFindOwnerKO() throws Exception {
		mockMvc.perform(get("/owners/666")).andExpect(status().isNotFound());
	}

	@Test
	void testCreateOwner() throws Exception {
		Owner owner = new Owner();
		owner.setFirstName("Ana");
		owner.setLastName("Lopez");
		owner.setAddress("Av. Central 123");
		owner.setCity("Lima");
		owner.setTelephone("5550101");

		mockMvc.perform(post("/owners").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(owner)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.lastName", is("Lopez")));
	}
}
