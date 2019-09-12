package ru.mentoring.rise.common.testutils.generator.dto

import org.assertj.core.api.Assertions.assertThat
import ru.mentoring.rise.common.dto.ResourceDto
import kotlin.test.Test

class ResourceDtoGeneratorTest {

    private val generator = ResourceDtoGenerator()

    @Test
    fun generate_shouldReturnResourceDtoWithoutNullFields() {

        assertThat(generator.generate())
                .isInstanceOf(ResourceDto::class.java)
                .hasNoNullFieldsOrProperties()
    }
}