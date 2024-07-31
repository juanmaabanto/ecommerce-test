package com.jabanto.process.order;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@Generated
@EnableRetry
@EnableReactiveMongoRepositories(basePackages = "com.jabanto.process.order.infrastructure.secondary.persistence")
public class ProcessOrderCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessOrderCommandApplication.class, args);
	}

}
