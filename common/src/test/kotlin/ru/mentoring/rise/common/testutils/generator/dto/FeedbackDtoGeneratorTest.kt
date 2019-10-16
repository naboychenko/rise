package ru.mentoring.rise.common.testutils.generator.dto

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import ru.mentoring.rise.common.dto.FeedbackDto
import kotlin.test.Test

class FeedbackDtoGeneratorTest {

    private val generator = FeedbackDtoGenerator()

    @Test
    fun generate_shouldReturnFeedbackDtoWithoutNullFields() {

        assertThat(generator.generate())
                .isInstanceOf(FeedbackDto::class.java)
                .hasNoNullFieldsOrProperties()
    }
}