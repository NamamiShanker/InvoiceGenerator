package com.example.dynamicPdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;

@SpringBootApplication(exclude = ThymeleafAutoConfiguration.class)
public class DynamicPdfApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicPdfApplication.class, args);
	}

}
