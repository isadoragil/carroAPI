package com.example.carroAPI;

import com.example.carroAPI.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarroApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CarroApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
