package com.tecsup.petclinic.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitDTO {
	private Long id;
	private Integer petId;
	private Integer vetId;
	private String visitDate;
	private String description;
	private BigDecimal cost;
}
