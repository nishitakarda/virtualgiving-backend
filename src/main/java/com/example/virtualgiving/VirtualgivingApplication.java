package com.example.virtualgiving;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class VirtualgivingApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualgivingApplication.class, args);
	}

}
