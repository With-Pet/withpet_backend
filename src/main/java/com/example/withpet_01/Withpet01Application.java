package com.example.withpet_01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Withpet01Application {

	public static void main(String[] args) {
		SpringApplication.run(Withpet01Application.class, args);

	}

}
