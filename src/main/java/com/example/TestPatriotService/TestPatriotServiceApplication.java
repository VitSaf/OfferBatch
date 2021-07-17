package com.example.TestPatriotService;

import com.example.TestPatriotService.Db.DbOperations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestPatriotServiceApplication {

	public static void main(String[] args) {
		DbOperations.tryDbConnection();
		SpringApplication.run(TestPatriotServiceApplication.class, args);
	}
}
