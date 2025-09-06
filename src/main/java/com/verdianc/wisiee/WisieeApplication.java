package com.verdianc.wisiee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WisieeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WisieeApplication.class, args);
	}

}
