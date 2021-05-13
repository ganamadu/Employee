package com.emp.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.emp.controller.EmployeeController;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.emp")
@EnableJpaRepositories("com.emp")
@EntityScan("com.emp")
@EnableFeignClients(basePackages = {"com.emp.client"})
public class EmployeeApplication {
	Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

}
