package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.services.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
class TypeControllerMockitoTest {
	@Autowired private MockMvc mockMvc;
	@MockitoBean private TypeService typeService;

	@Test
	void testFindAll() throws Exception {
		when(typeService.findAll()).thenReturn(List.of(new TypeDTO(1, "cat")));
		mockMvc.perform(get("/types"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is("cat")));
		verify(typeService).findAll();
	}

	@Test
	void testFindById() throws Exception {
		when(typeService.findById(1)).thenReturn(new TypeDTO(1, "cat"));
		mockMvc.perform(get("/types/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("cat")));
	}

	@Test
	void testFindByIdNotFound() throws Exception {
		when(typeService.findById(666)).thenReturn(null);
		mockMvc.perform(get("/types/666")).andExpect(status().isNotFound());
	}

	@Test
	void testCreate() throws Exception {
		when(typeService.create(org.mockito.ArgumentMatchers.any(TypeDTO.class)))
				.thenReturn(new TypeDTO(9, "fish"));
		mockMvc.perform(post("/types").contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"fish\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(9)))
				.andExpect(jsonPath("$.name", is("fish")));
	}

	@Test
	void testDelete() throws Exception {
		when(typeService.findById(8)).thenReturn(new TypeDTO(8, "temporary"));
		mockMvc.perform(delete("/types/8")).andExpect(status().isOk());
		verify(typeService).delete(8);
	}

	@Test
	void testDeleteNotFound() throws Exception {
		when(typeService.findById(1000)).thenReturn(null);
		mockMvc.perform(delete("/types/1000")).andExpect(status().isNotFound());
	}
}
