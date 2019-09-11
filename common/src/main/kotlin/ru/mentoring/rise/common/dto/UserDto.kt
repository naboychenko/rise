package ru.mentoring.rise.common.dto

data class UserDto (
        var id: String?=null,

        var username: String?=null,

        var password: String?=null,

        var email: String?=null,

        var position: String?=null,

        var level: String?=null,

        var role: RoleDto?=null
) : Dto