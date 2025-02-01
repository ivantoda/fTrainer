package com.ftrainer.ftrainer;

import com.ftrainer.ftrainer.entities.Grade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class FtrainerApplication {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(FtrainerApplication.class, args);
	}

}
