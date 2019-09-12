package ru.mentoring.rise.common.testutils.generator.dto

import ru.mentoring.rise.common.dto.UserDto
import ru.mentoring.rise.common.testutils.generator.Generator

class UserDtoGenerator : Generator<UserDto> {

    private val positionDtoGenerator = PositionDtoGenerator()
    private val levelDtoGenerator = LevelDtoGenerator()
    private val roleDtoGenerator = RoleDtoGenerator()

    override fun generate(): UserDto {
        return UserDto(
                id = generateString(),
                username = generateString(),
                password = generateString(),
                email = generateString(),
                position = positionDtoGenerator.generate(),
                level = levelDtoGenerator.generate(),
                role = roleDtoGenerator.generate()
        )
    }
}