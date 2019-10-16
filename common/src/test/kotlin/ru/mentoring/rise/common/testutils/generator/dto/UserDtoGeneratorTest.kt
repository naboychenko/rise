package ru.mentoring.rise.common.testutils.generator.dto

import org.assertj.core.api.Assertions.assertThat
import ru.mentoring.rise.common.dto.UserDto
import kotlin.test.Test

class UserDtoGeneratorTest {

    private val generator = UserDtoGenerator()

    @Test
    fun generate_shouldReturnUserDtoWithoutNullFields() {

        assertThat(generator.generate())
                .isInstanceOf(UserDto::class.java)
                .hasNoNullFieldsOrProperties()
    }
}