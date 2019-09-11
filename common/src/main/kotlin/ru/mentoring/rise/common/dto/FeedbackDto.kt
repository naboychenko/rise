package ru.mentoring.rise.common.dto

data class FeedbackDto (
        var id: String?=null,

        var author: UserDto?=null,

        var goal: GoalDto?=null,

        var description: String?=null
) : Dto