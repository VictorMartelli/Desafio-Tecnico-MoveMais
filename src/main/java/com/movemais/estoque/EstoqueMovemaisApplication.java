package com.movemais.estoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.movemais.estoque")
public class EstoqueMovemaisApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstoqueMovemaisApplication.class, args);
	}

}