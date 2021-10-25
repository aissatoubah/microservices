package com.urbanisation_microservices_produit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2 
public class MicroservicesProduitApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesProduitApplication.class, args);
	}

}
