package com.tecsup.petclinic.exceptions;

public class VisitNotFoundException extends RuntimeException {
	public VisitNotFoundException(Long id) {
		super("Visit not found: " + id);
	}
}
