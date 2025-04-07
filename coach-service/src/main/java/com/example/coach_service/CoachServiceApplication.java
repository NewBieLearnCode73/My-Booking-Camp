package com.example.coach_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CoachServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoachServiceApplication.class, args);
	}

}
