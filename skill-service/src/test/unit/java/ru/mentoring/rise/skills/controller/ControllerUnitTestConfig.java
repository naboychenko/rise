package ru.mentoring.rise.skills.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.mentoring.rise.common.testutils.generator.dto.SkillDtoGenerator;
import ru.mentoring.rise.skills.converter.SkillConverter;
import ru.mentoring.rise.skills.converter.SkillConverterImpl;

@TestConfiguration
public class ControllerUnitTestConfig {

    @Bean
    SkillDtoGenerator skillDtoGenerator() {
        return new SkillDtoGenerator();
    }

    @Bean
    SkillConverter skillConverter() {
        return new SkillConverterImpl();
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        return objectMapper;
    }
}
