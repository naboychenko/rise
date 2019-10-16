package ru.mentoring.rise.common.testutils.generator.dto

import ru.mentoring.rise.common.dto.StatusDto
import ru.mentoring.rise.common.testutils.generator.Generator

class StatusDtoGenerator : Generator<StatusDto> {
    override fun generate(): StatusDto {
        return StatusDto(
                id = generateString(),
                name = generateString()
        )
    }
}