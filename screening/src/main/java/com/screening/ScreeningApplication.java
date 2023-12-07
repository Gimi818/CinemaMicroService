package com.screening;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ScreeningApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScreeningApplication.class, args);
	}

}
