package ru.mentoring.rise.common.dto

data class RoleDto (
        var id: String?=null,

        var name: String?=null,

        var isAdmin: Boolean?=null,

        var isEditor: Boolean?=null
)