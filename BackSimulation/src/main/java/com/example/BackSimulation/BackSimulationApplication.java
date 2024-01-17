package com.example.BackSimulation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackSimulationApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackSimulationApplication.class, args);
	}

}
