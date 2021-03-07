package com.sevensenders.challenge.stripsfetcher;

import com.sevensenders.challenge.stripsfetcher.controller.StripsController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = StripsController.class)
public class StripsfetcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(StripsfetcherApplication.class, args);
	}

}
