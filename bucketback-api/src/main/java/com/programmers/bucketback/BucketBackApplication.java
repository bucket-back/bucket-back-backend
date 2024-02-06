package com.programmers.bucketback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan("com.programmers.bucketback.global.config.security.jwt")
@EnableScheduling
public class BucketBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BucketBackApplication.class, args);
	}

}
