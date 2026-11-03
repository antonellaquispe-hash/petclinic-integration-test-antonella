package com.tecsup.petclinic.exceptions;

public class SpecialtyNotFoundException extends RuntimeException {
	public SpecialtyNotFoundException(Integer id) {
		super("Specialty not found: " + id);
	}
}
