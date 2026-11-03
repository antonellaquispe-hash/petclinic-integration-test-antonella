package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.services.VisitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class VisitControllerMockitoTest {
	private static final ObjectMapper OM = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private VisitService visitService;

	@Test
	public void testFindAllVisits() throws Exception {
		when(visitService.findAll()).thenReturn(List.of(visit(1L, "rabies shot", 7, 2, "2010-03-04", "45.00")));
		mockMvc.perform(get("/visits")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].id", is(1)));
	}

	@Test
	public void testFindVisitOK() throws Exception {
		when(visitService.findById(1L)).thenReturn(visit(1L, "rabies shot", 7, 2, "2010-03-04", "45.00"));
		mockMvc.perform(get("/visits/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.description", is("rabies shot")));
	}

	@Test
	public void testFindVisitKO() throws Exception {
		when(visitService.findById(666L)).thenThrow(new VisitNotFoundException(666L));
		mockMvc.perform(get("/visits/666")).andExpect(status().isNotFound());
	}

	@Test
	public void testCreateVisit() throws Exception {
		VisitDTO input = visit(null, "new visit", 1, 1, "2024-03-10", "85.00");
		when(visitService.create(input)).thenReturn(visit(7L, "new visit", 1, 1, "2024-03-10", "85.00"));
		mockMvc.perform(post("/visits").content(OM.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(7)))
				.andExpect(jsonPath("$.description", is("new visit")));
	}

	@Test
	public void testUpdateVisit() throws Exception {
		VisitDTO input = visit(1L, "updated visit", 1, 1, "2024-03-11", "95.00");
		when(visitService.update(1L, input)).thenReturn(input);
		mockMvc.perform(put("/visits/1").content(OM.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.description", is("updated visit")));
	}

	@Test
	public void testDeleteVisit() throws Exception {
		doNothing().when(visitService).delete(1L);
		mockMvc.perform(delete("/visits/1")).andExpect(status().isOk());
	}

	@Test
	public void testFindByPetId() throws Exception {
		when(visitService.search(7, null, null, null))
				.thenReturn(List.of(visit(1L, "rabies shot", 7, 2, "2010-03-04", "45.00")));
		mockMvc.perform(get("/visits").param("petId", "7")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].petId", is(7)));
	}

	@Test
	public void testFindByVetId() throws Exception {
		when(visitService.search(null, 2, null, null))
				.thenReturn(List.of(visit(1L, "rabies shot", 7, 2, "2010-03-04", "45.00")));
		mockMvc.perform(get("/visits").param("vetId", "2")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].vetId", is(2)));
	}

	@Test
	public void testCalculateTotalCostByPet() throws Exception {
		when(visitService.totalCostByPet(7)).thenReturn(new BigDecimal("320.00"));
		mockMvc.perform(get("/visits/pet/7/total-cost")).andExpect(status().isOk())
				.andExpect(content().string("320.00"));
	}

	@Test
	public void testFindByDateBetween() throws Exception {
		when(visitService.search(null, null, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")))
				.thenReturn(List.of(visit(5L, "general checkup", 1, 1, "2024-01-15", "65.00")));
		mockMvc.perform(get("/visits").param("from", "2024-01-01").param("to", "2024-12-31"))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(5)));
	}

	@Test
	public void testFindByVetIdAndDateBetween() throws Exception {
		when(visitService.search( null, 1, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31")))
				.thenReturn(List.of(visit(5L, "general checkup", 1, 1, "2024-01-15", "65.00")));
		mockMvc.perform(get("/visits").param("vetId", "1").param("from", "2024-01-01")
					.param("to", "2024-12-31"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].vetId", is(1)));
	}

	@Test
	public void testCountVisitsByPet() throws Exception {
		when(visitService.countByPet(7)).thenReturn(2L);
		mockMvc.perform(get("/visits/pet/7/count")).andExpect(status().isOk())
				.andExpect(content().string("2"));
	}

	private VisitDTO visit(Long id, String description, Integer petId, Integer vetId, String date, String cost) {
		return VisitDTO.builder().id(id).description(description).petId(petId).vetId(vetId)
				.visitDate(date).cost(new BigDecimal(cost)).build();
	}
}
