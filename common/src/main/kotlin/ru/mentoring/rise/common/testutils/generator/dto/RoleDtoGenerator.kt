package ru.mentoring.rise.common.testutils.generator.dto

import ru.mentoring.rise.common.dto.RoleDto
import ru.mentoring.rise.common.testutils.generator.Generator
import java.util.*

class RoleDtoGenerator : Generator<RoleDto> {

    private val random = Random()

    override fun generate(): RoleDto {
        return RoleDto(
                id = generateString(),
                name = generateString(),
                isAdmin = random.nextBoolean(),
                isEditor = random.nextBoolean()
        )
    }
}