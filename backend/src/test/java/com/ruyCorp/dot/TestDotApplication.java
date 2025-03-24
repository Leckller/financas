package com.ruyCorp.dot;

import org.springframework.boot.SpringApplication;

public class TestDotApplication {

	public static void main(String[] args) {
		SpringApplication.from(DotApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
