package com.example.ms_verificaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsVerificacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsVerificacionesApplication.class, args);
	}

}
