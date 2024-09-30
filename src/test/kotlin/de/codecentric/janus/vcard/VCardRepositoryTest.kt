package de.codecentric.janus.vcard

import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.mockk.every
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.File
import kotlin.io.path.createTempDirectory
import kotlin.text.Charsets.UTF_8

@SpringBootTest
class VCardRepositoryTest {

    @Autowired
    private lateinit var subject: VCardRepository

    @MockkBean(relaxed = true)
    private lateinit var configuration: VCardConfiguration

    @Test
    @DisplayName("should read red vcard")
    fun shouldReadRedVCard() {
        val path = this::class.java.classLoader.getResource("vcard")?.path
        every { configuration.path } returns path.toString()

        subject.find("red") shouldBeEqual VCard(
            uid = "red",
            givenName = "Red",
            familyName = "Red",
            birthDate = LocalDate(2024, 8, 30),
            email = "red@codecentric.de",
            language = "DE",
            organization = "codecentric",
            role = "Unit Tester",
            phone = "+49 123 456789",
            revision = LocalDateTime(2024, 9, 30, 12, 0, 0, 0),
            url = "https://codecentric.de",
        )
    }


    @Test
    @DisplayName("should read blue vcard")
    fun shouldReadBlueVCard() {
        val path = this::class.java.classLoader.getResource("vcard")?.path
        every { configuration.path } returns path.toString()

        subject.find("blue") shouldBeEqual VCard(
            uid = "blue",
            givenName = "Blue",
            familyName = "Blue",
            birthDate = LocalDate(2024, 8, 30),
            email = "blue@codecentric.de",
            language = "DE",
            organization = "codecentric",
            role = "Unit Tester",
            phone = "+49 123 456789",
            revision = LocalDateTime(2024, 9, 30, 12, 0, 0, 0),
            url = "https://codecentric.de",
        )
    }

    @Test
    @DisplayName("should read all uid")
    fun shouldReadAllUid() {
        val path = this::class.java.classLoader.getResource("vcard")?.path
        every { configuration.path } returns path.toString()

        subject.findAllUid() shouldBeEqual setOf("red", "blue")
    }

    @Test
    @DisplayName("should read all")
    fun shouldReadAll() {
        val path = this::class.java.classLoader.getResource("vcard")?.path
        every { configuration.path } returns path.toString()

        subject.findAll().map { it.uid } shouldContainExactlyInAnyOrder setOf("red", "blue")
    }

    @Test
    @DisplayName("should write vcard to file")
    fun shouldWriteVCardToFile() {
        val vCard = VCard(
            uid = "apus",
            givenName = "Apus",
            familyName = "Apus",
            birthDate = LocalDate(2024, 8, 30),
            email = "apus@codecentric.de",
            language = "DE",
            organization = "codecentric",
            role = "Unit Tester",
            phone = "+49 123 456789",
            revision = LocalDateTime(2024, 9, 30, 12, 0, 0, 0),
            url = "https://codecentric.de",
        )

        with(createTempDirectory("apus").toFile()) {
            every { configuration.path } returns path
            deleteOnExit()

            subject.save(vCard)

            File("$path/apus.json").readText(UTF_8) shouldBe """
                {
                    "uid": "apus",
                    "givenName": "Apus",
                    "familyName": "Apus",
                    "birthDate": "2024-08-30",
                    "email": "apus@codecentric.de",
                    "language": "DE",
                    "organization": "codecentric",
                    "role": "Unit Tester",
                    "phone": "+49 123 456789",
                    "revision": "2024-09-30T12:00",
                    "url": "https://codecentric.de"
                }
            """.trimIndent()
        }
    }
}