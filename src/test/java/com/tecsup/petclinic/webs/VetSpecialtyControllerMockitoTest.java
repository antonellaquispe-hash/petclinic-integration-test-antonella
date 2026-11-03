package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.DuplicateVetSpecialtyException;
import com.tecsup.petclinic.services.VetSpecialtyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
class VetSpecialtyControllerMockitoTest {
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	@MockitoBean private VetSpecialtyService service;

	@Test
	void testAssignSpecialtyToVet() throws Exception {
		when(service.assignSpecialtyToVet(1, 3)).thenReturn(new VetSpecialty(1, 3));

		postAssignment(1, 3)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.vetId", is(1)))
				.andExpect(jsonPath("$.specialtyId", is(3)));
		verify(service).assignSpecialtyToVet(1, 3);
	}

	@Test
	void testRemoveSpecialtyFromVet() throws Exception {
		doNothing().when(service).removeSpecialtyFromVet(1, 2);
		mockMvc.perform(delete("/vet-specialties/1/2")).andExpect(status().isOk());
		verify(service).removeSpecialtyFromVet(1, 2);
	}

	@Test
	void testAssignDuplicate_ShouldFail() throws Exception {
		when(service.assignSpecialtyToVet(1, 3))
				.thenThrow(new DuplicateVetSpecialtyException("Duplicada"));
		postAssignment(1, 3).andExpect(status().isConflict());
		verify(service).assignSpecialtyToVet(1, 3);
	}

	private org.springframework.test.web.servlet.ResultActions postAssignment(int vetId, int specId)
			throws Exception {
		return mockMvc.perform(post("/vet-specialties")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(Map.of("vetId", vetId, "specId", specId))));
	}
}
