package com.group4.HaUISocialMedia_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HaUiSocialMediaServerApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");
		SpringApplication.run(HaUiSocialMediaServerApplication.class, args);
	}

}
