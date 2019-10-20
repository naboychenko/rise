package ru.mentoring.rise.skills.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.mentoring.rise.common.testutils.generator.dto.SkillDtoGenerator;

@TestConfiguration
public class IntegrationTestConfig {

    @Bean
    public SkillDtoGenerator generator() {
        return new SkillDtoGenerator();
    }
}
