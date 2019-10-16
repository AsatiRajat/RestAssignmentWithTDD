package com.yash.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class RestAssignmentWithTddApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestAssignmentWithTddApplication.class, args);
	}

}
