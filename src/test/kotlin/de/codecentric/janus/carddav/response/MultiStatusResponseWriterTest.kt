package de.codecentric.janus.carddav.response

import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.redundent.kotlin.xml.xml

class MultiStatusResponseWriterTest {

    private val content = """
        LINE_1
        LINE_2
    """.trimIndent()

    private val source = MultiStatusResponse(
        responses = listOf(
            StatusResponse(
                href = "/path/to/resource",
                ok = listOf(
                    xml("prop") {
                        text(content)
                    }
                ),
                notFound = emptyList()
            )
        )
    )

    private val subject = MultiStatusResponseWriter(source)

    @Test
    @DisplayName("should write multiline content")
    fun shouldWriteMultilineContent() {
        val result = subject.write().toString()
        result shouldContain  "LINE_1\nLINE_2"
    }
}