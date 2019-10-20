package ru.mentoring.rise.skills;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.mentoring.rise.skills.config.IntegrationTestConfig;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {SkillsApplication.class, IntegrationTestConfig.class}
)
@ActiveProfiles("integration-test")
public abstract class SkillBaseIntegrationTest {
}
