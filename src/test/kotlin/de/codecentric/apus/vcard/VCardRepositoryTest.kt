package de.codecentric.apus.vcard

import com.ninjasquad.springmockk.MockkBean
import de.codecentric.apus.vcard.VCard.Companion.vcard
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.equality.shouldBeEqualUsingFields
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

        subject.find("red") shouldBeEqualUsingFields vcard {
            meta {
                uid = "red"
                rev = LocalDateTime(2024, 9, 30, 12, 0)
            }

            bio {
                givenName = "Red"
                familyName = "Red"
                birthday = LocalDate(2024, 8, 30)
            }

            contact {
                email = "red@codecentric.de"
                cellPhone = "+49 123 456789"
            }

            additionalInfo {
                githubUrl = "https://github.com/red"
            }

            organization {
                name = "codecentric"
                branch = "Software Development"
                division = "Dev Team Red"
            }
        }
    }

    @Test
    @DisplayName("should read blue vcard")
    fun shouldReadBlueVCard() {
        val path = this::class.java.classLoader.getResource("vcard")?.path
        every { configuration.path } returns path.toString()

        subject.find("blue") shouldBeEqualUsingFields vcard {
            meta {
                uid = "blue"
                rev = LocalDateTime(2024, 9, 30, 12, 0)
            }

            bio {
                givenName = "Blue"
                familyName = "Blue"
                birthday = LocalDate(2024, 8, 30)
            }

            contact {
                email = "blue@codecentric.de"
                cellPhone = "+49 123 456789"
            }

            additionalInfo {
                githubUrl = "https://github.com/blue"
            }

            organization {
                name = "codecentric"
                branch = "Software Development"
                division = "Dev Team Blue"
            }
        }
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
        val vCard = vcard {
            meta {
                uid = "apus"
                rev = LocalDateTime(2024, 10, 2, 15, 47, 38, 47795)
            }

            bio {
                givenName = "Apus"
                familyName = "Apus"
                birthday = LocalDate(2024, 8, 30)
            }

            contact {
                email = "apus@codecentric.de"
                cellPhone = "+49 123 456789"
            }

            organization {
                name = "codecentric";
                branch = "Software Development"
                division = "Dev Team"
            }

            additionalInfo {
                githubUrl = "https://github.com/codecentric/apus"
            }
        }

        with(createTempDirectory("apus").toFile()) {
            every { configuration.path } returns path
            deleteOnExit()

            subject.save(vCard)

            File("$path/${vCard.persistenceName}.json").readText(UTF_8) shouldBe """
                {
                    "meta": {
                        "rev": "2024-10-02T15:47:38.000047795",
                        "uid": "apus"
                    },
                    "bio": {
                        "givenName": "Apus",
                        "familyName": "Apus",
                        "birthday": "2024-08-30"
                    },
                    "contact": {
                        "email": "apus@codecentric.de",
                        "cellPhone": "+49 123 456789"
                    },
                    "additionalInfo": {
                        "githubUrl": "https://github.com/codecentric/apus"
                    },
                    "organization": {
                        "name": "codecentric",
                        "branch": "Software Development",
                        "division": "Dev Team"
                    }
                }
            """.trimIndent()
        }
    }
}