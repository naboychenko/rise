package ru.mentoring.rise.common.testutils.generator.dto

import ru.mentoring.rise.common.dto.LevelDto
import ru.mentoring.rise.common.testutils.generator.Generator

class LevelDtoGenerator : Generator<LevelDto> {
    override fun generate(): LevelDto {
        return LevelDto(
                id = generateString(),
                name = generateString()
        )
    }
}