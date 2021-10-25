package com.urbanisation_microservices_produit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProduitIntrouvableException extends Exception {
	
	public ProduitIntrouvableException(String message) {
		super(message);
	}
	
}
