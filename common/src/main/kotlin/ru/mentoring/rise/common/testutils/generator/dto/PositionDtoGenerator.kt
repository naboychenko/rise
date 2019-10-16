package ru.mentoring.rise.common.testutils.generator.dto

import ru.mentoring.rise.common.dto.PositionDto
import ru.mentoring.rise.common.testutils.generator.Generator

class PositionDtoGenerator : Generator<PositionDto> {
    override fun generate(): PositionDto {
        return PositionDto(
                id = generateString(),
                name = generateString()
        )
    }
}