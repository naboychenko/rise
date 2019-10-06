package ru.mentoring.rise.skills;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableMongoRepositories(basePackages = "ru.mentoring.rise.skills.repo.mongo")
@EnableJpaRepositories(basePackages = "ru.mentoring.rise.skills.repo.jpa")
@EnableJpaAuditing
public class SkillsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkillsApplication.class, args);
    }
}
