package ru.mentoring.rise.common.testutils.generator.dto

import ru.mentoring.rise.common.dto.FeedbackDto
import ru.mentoring.rise.common.testutils.generator.Generator

class FeedbackDtoGenerator : Generator<FeedbackDto> {

    private val userDtoGenerator = UserDtoGenerator()
    private val goalDtoGenerator = GoalDtoGenerator()

    override fun generate(): FeedbackDto {
        return FeedbackDto(
                id = generateString(),
                author = userDtoGenerator.generate(),
                goal = goalDtoGenerator.generate(),
                description = generateString()
        )
    }
}