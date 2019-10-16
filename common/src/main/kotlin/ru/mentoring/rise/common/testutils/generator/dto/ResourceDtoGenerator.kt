package ru.mentoring.rise.common.testutils.generator.dto

import ru.mentoring.rise.common.dto.ResourceDto
import ru.mentoring.rise.common.testutils.generator.Generator

class ResourceDtoGenerator : Generator<ResourceDto> {
    override fun generate(): ResourceDto {
        return ResourceDto(
                id = generateString(),
                name = generateString(),
                url = generateString(),
                description = generateString()
        )
    }
}