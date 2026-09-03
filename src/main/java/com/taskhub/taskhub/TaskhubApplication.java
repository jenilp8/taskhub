package com.taskhub.taskhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TaskhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskhubApplication.class, args);
	}

}
