package com.communication.openfignms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.communication.openfignms")
public class OpenfignmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenfignmsApplication.class, args);
	}

}
