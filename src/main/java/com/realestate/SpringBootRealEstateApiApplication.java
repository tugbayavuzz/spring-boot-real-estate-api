package com.realestate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringBootRealEstateApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRealEstateApiApplication.class, args);
	}

}
