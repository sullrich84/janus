package de.codecentric.apus.vcard

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import kotlin.test.Test

@SpringBootTest
@Import(VCardConfiguration::class)
@ContextConfiguration(classes = [VCardService::class, VCardRepository::class])
class VCardServiceTest {

    @Autowired
    lateinit var subject: VCardService

    @Test
    @DisplayName("should get latest sync token")
    fun shouldGetLatestSyncToken() {
        subject.getLatestSyncToken() shouldBe "2024-09-30T12:00"
    }

    @Test
    @DisplayName("should get latest ctag")
    fun shouldGetLatestCTag() {
        subject.getLatestCTag() shouldBe "2024-09-30T12:00"
    }
}