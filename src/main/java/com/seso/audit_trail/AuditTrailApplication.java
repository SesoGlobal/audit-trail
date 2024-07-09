package com.seso.audit_trail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class AuditTrailApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditTrailApplication.class, args);
	}

}
