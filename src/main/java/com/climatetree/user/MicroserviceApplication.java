package com.climatetree.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("./application-local.properties")
@SpringBootApplication
public class MicroserviceApplication {

	public static void main(String[] args) {
			SpringApplication.run(MicroserviceApplication.class, args);

	}

}
