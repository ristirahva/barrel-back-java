package org.km;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BarrelApplication {
    public static void main(String[] args) {
        SpringApplication.run(BarrelApplication.class, args);
    }
}