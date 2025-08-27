package com.example.skillsnap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.skillsnap.model")
@EnableJpaRepositories(basePackages = "com.example.skillsnap.repository")
public class SkillsnapApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkillsnapApplication.class, args);
    }

}
