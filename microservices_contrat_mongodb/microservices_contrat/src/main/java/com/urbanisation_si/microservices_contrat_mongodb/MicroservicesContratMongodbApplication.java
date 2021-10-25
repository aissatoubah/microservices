package com.urbanisation_si.microservices_contrat_mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableSwagger2 
public class MicroservicesContratMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesContratMongodbApplication.class, args);
	}

}
