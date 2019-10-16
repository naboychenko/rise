package ru.mentoring.rise.common.testutils.generator.dto

import org.assertj.core.api.Assertions.assertThat
import ru.mentoring.rise.common.dto.LevelDto
import kotlin.test.Test

class LevelDtoGeneratorTest {

    private val generator = LevelDtoGenerator()

    @Test
    fun generate_shouldReturnLevelDtoWithoutNullFields() {

        assertThat(generator.generate())
                .isInstanceOf(LevelDto::class.java)
                .hasNoNullFieldsOrProperties()
    }
}