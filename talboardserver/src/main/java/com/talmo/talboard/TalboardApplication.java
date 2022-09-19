package com.talmo.talboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({FileUploadProperties.class})
@SpringBootApplication
public class TalboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalboardApplication.class, args);
	}

}
