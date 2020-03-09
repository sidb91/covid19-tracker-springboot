package com.opolo.coronatrackerapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoronaTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoronaTrackingApplication.class, args);
	}

}
