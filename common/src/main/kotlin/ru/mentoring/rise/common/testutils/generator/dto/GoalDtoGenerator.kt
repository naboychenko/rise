package ru.mentoring.rise.common.testutils.generator.dto

import ru.mentoring.rise.common.dto.FeedbackDto
import ru.mentoring.rise.common.dto.GoalDto
import ru.mentoring.rise.common.testutils.generator.Generator

class GoalDtoGenerator : Generator<GoalDto> {

    private val userDtoGenerator = UserDtoGenerator()
    private val resourceDtoGenerator = ResourceDtoGenerator()
    private val statusDtoGenerator = StatusDtoGenerator()

    override fun generate(): GoalDto {
        return GoalDto(
                id = generateString(),
                user = userDtoGenerator.generate(),
                name = generateString(),
                resource = resourceDtoGenerator.generate(),
                status = statusDtoGenerator.generate(),
                feedback = HashSet<FeedbackDto>()
        )
    }
}