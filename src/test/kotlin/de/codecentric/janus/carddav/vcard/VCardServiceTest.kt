package de.codecentric.janus.carddav.vcard

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import kotlin.test.Test

@Disabled
@SpringBootTest
@ContextConfiguration(classes = [VCardService::class])
class VCardServiceTest {

    @Autowired
    lateinit var subject: VCardService

    @Test
    @DisplayName("should get latest sync token")
    fun shouldGetLatestSyncToken() {
        subject.getLatestSyncToken() shouldBe "SYNC_TOKEN_NOT_IMPLEMENTED"
    }

    @Test
    @DisplayName("should get latest ctag")
    fun shouldGetLatestCTag() {
        subject.getLatestCTag() shouldBe "CTAG_NOT_IMPLEMENTED"
    }
}