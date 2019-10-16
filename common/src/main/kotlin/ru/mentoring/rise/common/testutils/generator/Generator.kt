package ru.mentoring.rise.common.testutils.generator

import java.util.*

interface Generator<T> {
    fun generate(): T

    fun generateString(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }
}