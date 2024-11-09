package com.example.dynamicPdf;

import com.example.dynamicPdf.configs.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication(exclude = ThymeleafAutoConfiguration.class)
public class DynamicPdfApplication {

	public static void main(String[] args) {
		try {
			Files.createDirectories(Paths.get(Constants.INVOICES_DIRECTORY));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		SpringApplication.run(DynamicPdfApplication.class, args);
	}

}
