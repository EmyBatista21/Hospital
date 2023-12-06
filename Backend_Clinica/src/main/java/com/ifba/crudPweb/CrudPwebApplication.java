package com.ifba.crudPweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ifba.crudPweb")
public class CrudPwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudPwebApplication.class, args);
	}

}

