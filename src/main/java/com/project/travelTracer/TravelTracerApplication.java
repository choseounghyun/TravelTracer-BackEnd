package com.project.travelTracer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TravelTracerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelTracerApplication.class, args);
	}

}
