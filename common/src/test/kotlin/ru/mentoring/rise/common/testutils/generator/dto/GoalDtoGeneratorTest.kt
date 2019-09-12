package ru.mentoring.rise.common.testutils.generator.dto

import org.assertj.core.api.Assertions.assertThat
import ru.mentoring.rise.common.dto.GoalDto
import kotlin.test.Test

class GoalDtoGeneratorTest {

    private val generator = GoalDtoGenerator()

    @Test
    fun generate_shouldReturnGoalDtoWithoutNullFields() {

        assertThat(generator.generate())
                .isInstanceOf(GoalDto::class.java)
                .hasNoNullFieldsOrProperties()
    }
}