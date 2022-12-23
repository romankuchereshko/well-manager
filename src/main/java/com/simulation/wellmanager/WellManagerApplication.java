package com.simulation.wellmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class WellManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WellManagerApplication.class, args);
		log.info("Running...");
	}

}
