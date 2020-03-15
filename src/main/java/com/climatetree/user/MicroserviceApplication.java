package com.climatetree.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroserviceApplication {

	public static void main(String[] args) {
		System.out.println("In class Main");
			SpringApplication.run(MicroserviceApplication.class, args);
	}

}
