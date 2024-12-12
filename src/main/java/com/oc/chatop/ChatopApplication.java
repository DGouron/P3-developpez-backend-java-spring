package com.oc.chatop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.oc.chatop.repositories")
@SpringBootApplication
public class ChatopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatopApplication.class, args);
	}
}