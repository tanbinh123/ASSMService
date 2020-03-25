package com.aeon.mm.main.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@SpringBootApplication
public class AssmServiceApplication {

	@RequestMapping("/assm")
	String home() {
		return "Hello world";
	}

	public static void main(String[] args) {
		SpringApplication.run(AssmServiceApplication.class, args);
	}
}
