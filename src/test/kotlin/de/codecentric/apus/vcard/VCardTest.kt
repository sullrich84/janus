package de.codecentric.apus.vcard

import de.codecentric.apus.vcard.VCard.Companion.vcard
import io.kotest.assertions.withClue
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atTime
import org.junit.jupiter.api.Nested
import kotlin.test.Test

class VCardTest {

    val vCard = vcard {
        meta {
            version = "4.0"
            rev = LocalDate(2024, 9, 30).atTime(LocalTime(12, 0))
            uid = "john-doe-12345"
        }

        bio {
            title = "Consultant Developer"
            fullName = "Dr. John 'Johnny' Doe"
            nickname = "Johnny"
            givenName = "John"
            familyName = "Doe"
            honorific = "Dr."
            birthday = LocalDate(1980, 1, 1)
        }

        organization {
            name = "codecentric"
            branch = "Software Development"
            division = "Consulting"
            anniversary = LocalDate(2000, 1, 1)
        }

        address {
            street = "Hochstraße 11"
            postalCode = "42697"
            city = "Solingen"
            state = "Nordrhein-Westfalen"
            country = "Deutschland"
        }

        contact {
            email = "john.doe@codecentric.de"
            cellPhone = "+49 212 1234567"
        }

        additionalInfo {
            githubUrl = "https://github.com/codecentric"
            gitlabUrl = "https://gitlab.com/codecentric"
            atlasUrl = "https://atlas.codecentric.de/johndoe"
        }
    }

    val subject = vCard.toVCF().trim().split("\n")

    val snapshot = this::class.java.getResource("/snapshot/vcard.vcf")
        ?.readText()!!
        .split("\n")

    @Nested
    inner class `Given vCard and snapshot` {

        @Test
        fun `should have identical line size`() {
            subject.size shouldBe snapshot.size
        }

        @Test
        fun `should have each line identical`() {
            subject.indices.forEach { line ->
                withClue("Comparing line $line") {
                    subject[line] shouldBe snapshot[line]
                }
            }
        }
    }
}