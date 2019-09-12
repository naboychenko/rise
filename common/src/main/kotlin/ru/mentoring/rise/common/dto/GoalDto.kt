package ru.mentoring.rise.common.dto

data class GoalDto (
        var id: String?=null,

        var user: UserDto?=null,

        var name: String?=null,

        var resource: ResourceDto?=null,

        var status: StatusDto?=null,

        var feedback: Set<FeedbackDto>?=null
) : Dto