package com.boot.axon.cqrs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class AxonCqrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AxonCqrsApplication.class, args);
	}

}