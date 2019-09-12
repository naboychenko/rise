package ru.mentoring.rise.common.testutils.generator.dto

import org.assertj.core.api.Assertions.assertThat
import ru.mentoring.rise.common.dto.PositionDto
import kotlin.test.Test

class PositionDtoGeneratorTest {

    private val generator = PositionDtoGenerator()

    @Test
    fun generate_shouldReturnPositionDtoWithoutNullFields() {

        assertThat(generator.generate())
                .isInstanceOf(PositionDto::class.java)
                .hasNoNullFieldsOrProperties()
    }
}