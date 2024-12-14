package com.restock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RestockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestockApplication.class, args);
    }

}
