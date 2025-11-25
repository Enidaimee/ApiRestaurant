package com.upiiz.ApiRestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.upiiz.ApiRestaurant.Controllers",
        "com.upiiz.ApiRestaurant.Services",
        "com.upiiz.ApiRestaurant.Repositories"})

public class ApiRestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRestaurantApplication.class, args);
	}

}
