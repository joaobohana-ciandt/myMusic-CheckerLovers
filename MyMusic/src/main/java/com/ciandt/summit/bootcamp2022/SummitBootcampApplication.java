package com.ciandt.summit.bootcamp2022;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients
@SpringBootApplication
@EnableCaching
public class SummitBootcampApplication {

	public static void main(String[] args) {
		SpringApplication.run(SummitBootcampApplication.class, args);
	}

}
