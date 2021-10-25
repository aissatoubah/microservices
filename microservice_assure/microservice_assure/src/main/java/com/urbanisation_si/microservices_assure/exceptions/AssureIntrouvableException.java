package com.urbanisation_si.microservices_assure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AssureIntrouvableException extends Exception  {


	public AssureIntrouvableException(String message) {
		super(message);
	}
	
}
