package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.dtos.VisitDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VisitControllerTest {
	private static final ObjectMapper OM = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testFindAllVisits() throws Exception {
		mockMvc.perform(get("/visits"))
				.andExpect(status().isOk())
				//.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(6)))
				.andExpect(jsonPath("$[0].id", is(1)));
	}

	@Test
	public void testFindVisitOK() throws Exception {
		mockMvc.perform(get("/visits/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.description", is("rabies shot")))
				.andExpect(jsonPath("$.petId", is(7)));
	}

	@Test
	public void testFindVisitKO() throws Exception {
		mockMvc.perform(get("/visits/666")).andExpect(status().isNotFound());
	}

	@Test
	public void testCreateVisit() throws Exception {
		MvcResult result = mockMvc.perform(post("/visits").content(OM.writeValueAsString(visit(null,
					"new visit", 1, 1, "2024-03-10", "85.00")))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.description", is("new visit")))
				.andExpect(jsonPath("$.petId", is(1))).andReturn();
		Long id = OM.readTree(result.getResponse().getContentAsString()).get("id").asLong();
		mockMvc.perform(delete("/visits/{id}", id)).andExpect(status().isOk());
	}

	@Test
	public void testUpdateVisit() throws Exception {
		Long id = createVisit("before update");
		mockMvc.perform(put("/visits/{id}", id).content(OM.writeValueAsString(visit(id,
					"after update", 1, 1, "2024-03-11", "95.00")))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.description", is("after update")));
		mockMvc.perform(get("/visits/{id}", id)).andExpect(status().isOk())
				.andExpect(jsonPath("$.description", is("after update")));
		mockMvc.perform(delete("/visits/{id}", id)).andExpect(status().isOk());
	}

	@Test
	public void testDeleteVisit() throws Exception {
		Long id = createVisit("visit to delete");
		mockMvc.perform(delete("/visits/{id}", id)).andExpect(status().isOk());
		mockMvc.perform(get("/visits/{id}", id)).andExpect(status().isNotFound());
	}

	@Test
	public void testFindByPetId() throws Exception {
		mockMvc.perform(get("/visits").param("petId", "7"))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].petId", is(7)));
	}

	@Test
	public void testFindByVetId() throws Exception {
		mockMvc.perform(get("/visits").param("vetId", "2"))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].vetId", is(2)));
	}

	@Test
	public void testCalculateTotalCostByPet() throws Exception {
		mockMvc.perform(get("/visits/pet/7/total-cost"))
				.andExpect(status().isOk()).andExpect(content().string("320.00"));
	}

	@Test
	public void testFindByDateBetween() throws Exception {
		mockMvc.perform(get("/visits").param("from", "2024-01-01").param("to", "2024-12-31"))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(5)));
	}

	@Test
	public void testFindByVetIdAndDateBetween() throws Exception {
		mockMvc.perform(get("/visits").param("vetId", "1")
					.param("from", "2024-01-01").param("to", "2024-12-31"))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(5)));
	}

	@Test
	public void testCountVisitsByPet() throws Exception {
		mockMvc.perform(get("/visits/pet/7/count"))
				.andExpect(status().isOk()).andExpect(content().string("2"));
	}

	private Long createVisit(String description) throws Exception {
		MvcResult result = mockMvc.perform(post("/visits").content(OM.writeValueAsString(visit(null,
					description, 1, 1, "2024-03-12", "80.00")))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();
		return OM.readTree(result.getResponse().getContentAsString()).get("id").asLong();
	}

	private VisitDTO visit(Long id, String description, Integer petId, Integer vetId, String date, String cost) {
		return VisitDTO.builder().id(id).description(description).petId(petId).vetId(vetId)
				.visitDate(date).cost(new BigDecimal(cost)).build();
	}
}
