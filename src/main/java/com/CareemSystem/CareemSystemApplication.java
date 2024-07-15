package com.CareemSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@CrossOrigin(origins = "*")
public class CareemSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareemSystemApplication.class, args);
	}

}

