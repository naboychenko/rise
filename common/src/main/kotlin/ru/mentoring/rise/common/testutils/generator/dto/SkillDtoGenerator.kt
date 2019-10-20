package ru.mentoring.rise.common.testutils.generator.dto

import ru.mentoring.rise.common.dto.SkillDto
import ru.mentoring.rise.common.testutils.generator.Generator
import java.util.*

class SkillDtoGenerator : Generator<SkillDto> {

    private val random = Random()

    override fun generate(): SkillDto {
        return SkillDto(
                id = random.nextLong(),
                createdAt = Date(),
                updatedAt = Date(),
                skillName = generateString(),
                level = random.nextInt(9) +1,
                rank = "NOVICE"
        )
    }
}