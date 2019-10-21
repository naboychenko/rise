package ru.mentoring.rise.common.dto

import java.util.Date

data class SkillDto(

        var id: Long? = null,

        var createdAt: Date? = null,

        var updatedAt: Date? = null,

        var skillName: String? = null,

        var level: Int? = null,

        var rank: String? = null

) : Dto