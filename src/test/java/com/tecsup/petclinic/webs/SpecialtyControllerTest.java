package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.dtos.SpecialtyDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class SpecialtyControllerTest {
	private static final ObjectMapper OM = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testFindAllSpecialties() throws Exception {
		mockMvc.perform(get("/specialties"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].id", is(1)));
	}

	@Test
	public void testFindSpecialtyOK() throws Exception {
		mockMvc.perform(get("/specialties/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name", is("radiology")));
	}

	@Test
	public void testFindSpecialtyKO() throws Exception {
		mockMvc.perform(get("/specialties/666")).andExpect(status().isNotFound());
	}

	@Test
	public void testCreateSpecialty() throws Exception {
		SpecialtyDTO specialty = specialty("cardiology", "Madison", 7, 17);
		MvcResult result = mockMvc.perform(post("/specialties").content(OM.writeValueAsString(specialty))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is("cardiology")))
				.andExpect(jsonPath("$.office", is("Madison")))
				.andExpect(jsonPath("$.hOpen", is(7)))
				.andExpect(jsonPath("$.hClose", is(17))).andReturn();
		Integer id = OM.readTree(result.getResponse().getContentAsString()).get("id").asInt();
		mockMvc.perform(delete("/specialties/{id}", id)).andExpect(status().isOk());
	}

	@Test
	public void testFindByName() throws Exception {
		mockMvc.perform(get("/specialties").param("name", "surgery"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", is("surgery")));
	}

	@Test
	public void testFindByOffice() throws Exception {
		mockMvc.perform(get("/specialties").param("office", "Farewell"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is("radiology")));
	}

	@Test
	public void testCreateSpecialty_InvalidSchedule() throws Exception {
		SpecialtyDTO specialty = specialty("invalid schedule", "Madison", 12, 8);
		mockMvc.perform(post("/specialties").content(OM.writeValueAsString(specialty))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testCreateSpecialty_InvalidHourRange() throws Exception {
		SpecialtyDTO specialty = specialty("invalid hours", "Madison", 24, 28);
		mockMvc.perform(post("/specialties").content(OM.writeValueAsString(specialty))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testDeleteSpecialty() throws Exception {
		MvcResult result = mockMvc.perform(post("/specialties").content(OM.writeValueAsString(
					specialty("temporary specialty", "Madison", 7, 17)))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();
		Integer id = OM.readTree(result.getResponse().getContentAsString()).get("id").asInt();
		mockMvc.perform(delete("/specialties/{id}", id)).andExpect(status().isOk());
	}

	private SpecialtyDTO specialty(String name, String office, int open, int close) {
		return SpecialtyDTO.builder().name(name).office(office).hOpen(open).hClose(close).build();
	}
}
