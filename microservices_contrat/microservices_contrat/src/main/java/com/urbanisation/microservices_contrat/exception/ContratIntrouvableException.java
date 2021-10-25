package com.urbanisation.microservices_contrat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContratIntrouvableException extends Exception {

	public ContratIntrouvableException(String message) {
		super(message);
	}
}
