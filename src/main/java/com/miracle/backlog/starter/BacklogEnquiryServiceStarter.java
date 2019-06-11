package com.miracle.backlog.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan({ "com.miracle.database.connection", "com.miracle.controller", "com.miracle.config",
		"com.miracle.utility", "com.miracle.backlog.*" })
@EnableSwagger2
//@EnableMongoRepositories({ "com.miracle.database.connection" })
public class BacklogEnquiryServiceStarter extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BacklogEnquiryServiceStarter.class, args);
	}

	@Override
	public SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BacklogEnquiryServiceStarter.class);
	}

}
