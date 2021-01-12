package com.bookmyshow.bookmyshowapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookMyShowApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookMyShowApiApplication.class, args);
    }

}
