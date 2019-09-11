package ru.mentoring.rise.common.dto

data class ResourceDto (
        var id: String?=null,

        var name: String?=null,

        var url: String?=null,

        var description: String?=null
) : Dto