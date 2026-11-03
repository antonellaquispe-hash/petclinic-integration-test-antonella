package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.services.SpecialtyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class SpecialtyControllerMockitoTest {
	private static final ObjectMapper OM = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private SpecialtyService specialtyService;

	@Test
	public void testFindAllSpecialties() throws Exception {
		when(specialtyService.findAll()).thenReturn(List.of(specialty(1, "radiology", "Farewell", 8, 18)));
		mockMvc.perform(get("/specialties"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(1)));
	}

	@Test
	public void testFindSpecialtyOK() throws Exception {
		when(specialtyService.findById(1)).thenReturn(specialty(1, "radiology", "Farewell", 8, 18));
		mockMvc.perform(get("/specialties/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("radiology")));
	}

	@Test
	public void testFindSpecialtyKO() throws Exception {
		when(specialtyService.findById(666)).thenThrow(new SpecialtyNotFoundException(666));
		mockMvc.perform(get("/specialties/666")).andExpect(status().isNotFound());
	}

	@Test
	public void testCreateSpecialty() throws Exception {
		SpecialtyDTO input = specialty(null, "cardiology", "Madison", 7, 17);
		when(specialtyService.create(input)).thenReturn(specialty(4, "cardiology", "Madison", 7, 17));
		mockMvc.perform(post("/specialties").content(OM.writeValueAsString(input))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is("cardiology")))
				.andExpect(jsonPath("$.id", is(4)));
	}

	@Test
	public void testFindByName() throws Exception {
		when(specialtyService.findByName("surgery"))
				.thenReturn(List.of(specialty(2, "surgery", "Maryland", 8, 12)));
		mockMvc.perform(get("/specialties").param("name", "surgery"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].name", is("surgery")));
	}

	@Test
	public void testFindByOffice() throws Exception {
		when(specialtyService.findByOffice("Farewell"))
				.thenReturn(List.of(specialty(1, "radiology", "Farewell", 8, 18)));
		mockMvc.perform(get("/specialties").param("office", "Farewell"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].name", is("radiology")));
	}

	@Test
	public void testCreateSpecialty_InvalidSchedule() throws Exception {
		SpecialtyDTO input = specialty(null, "invalid", "Madison", 12, 8);
		mockMvc.perform(post("/specialties").content(OM.writeValueAsString(input))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testCreateSpecialty_InvalidHourRange() throws Exception {
		SpecialtyDTO input = specialty(null, "invalid", "Madison", 24, 28);
		mockMvc.perform(post("/specialties").content(OM.writeValueAsString(input))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testDeleteSpecialty() throws Exception {
		doNothing().when(specialtyService).delete(1);
		mockMvc.perform(delete("/specialties/1")).andExpect(status().isOk());
	}

	private SpecialtyDTO specialty(Integer id, String name, String office, int open, int close) {
		return SpecialtyDTO.builder().id(id).name(name).office(office).hOpen(open).hClose(close).build();
	}
}
