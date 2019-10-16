package ru.mentoring.rise.common.testutils.generator.dto

import org.assertj.core.api.Assertions.assertThat
import ru.mentoring.rise.common.dto.RoleDto
import kotlin.test.Test

class RoleDtoGeneratorTest {

    private val generator = RoleDtoGenerator()

    @Test
    fun generate_shouldReturnRoleDtoWithoutNullFields() {

        assertThat(generator.generate())
                .isInstanceOf(RoleDto::class.java)
                .hasNoNullFieldsOrProperties()
    }
}