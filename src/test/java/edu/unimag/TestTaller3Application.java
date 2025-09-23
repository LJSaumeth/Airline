package edu.unimag;

import org.springframework.boot.SpringApplication;

import edu.unimag.Taller3Application;

public class TestTaller3Application {

	public static void main(String[] args) {
		SpringApplication.from(Taller3Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
