package ru.mentoring.rise.common.testutils.generator.dto

import org.assertj.core.api.Assertions.assertThat
import ru.mentoring.rise.common.dto.StatusDto
import kotlin.test.Test

class StatusDtoGeneratorTest {

    private val generator = StatusDtoGenerator()

    @Test
    fun generate_shouldReturnStatusDtoWithoutNullFields() {

        assertThat(generator.generate())
                .isInstanceOf(StatusDto::class.java)
                .hasNoNullFieldsOrProperties()
    }
}