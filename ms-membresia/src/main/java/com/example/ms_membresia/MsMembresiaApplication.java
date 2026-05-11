package com.example.ms_membresia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients 
public class MsMembresiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsMembresiaApplication.class, args);
	}

}
