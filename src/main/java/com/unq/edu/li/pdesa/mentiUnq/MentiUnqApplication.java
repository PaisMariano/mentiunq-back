package com.unq.edu.li.pdesa.mentiUnq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.origin.SystemEnvironmentOrigin;

@SpringBootApplication
public class MentiUnqApplication {

	public static void main(String[] args) {
		SpringApplication.run(MentiUnqApplication.class, args);
		System.out.println("Hola mundo");
	}

}
