package ru.mentoring.rise.common.testutils.generator.dto;

import org.assertj.core.api.Assertions
import ru.mentoring.rise.common.dto.SkillDto
import kotlin.test.Test

class SkillDtoGeneratorTest {

    private val generator = SkillDtoGenerator();

    @Test
    fun generate_shouldReturnSkillDtoWithoutNullFields() {
        Assertions.assertThat(generator.generate())
                .isInstanceOf(SkillDto::class.java)
                .hasNoNullFieldsOrProperties()
    }

}
