package de.codecentric.apus.vcard

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class VCard {
    var meta: Meta = Meta()
    var bio: Bio? = null
    var contact: Contact? = null
    var additionalInfo: AdditionalInfo? = null
    var organization: Organization? = null
    var address: Address? = null

    val uid: String
        get() = meta.uid

    val persistenceName: String
        get() = "${bio?.familyName?.lowercase().plus("-")}${meta.uid}.vcard"

    val updatedAt: LocalDateTime
        get() = meta.rev

    fun meta(block: Meta.() -> Unit) {
        meta = Meta().apply(block)
    }

    fun bio(block: Bio.() -> Unit) {
        bio = Bio().apply(block)
    }

    fun address(block: Address.() -> Unit) {
        address = Address().apply(block)
    }

    fun organization(block: Organization.() -> Unit) {
        organization = Organization().apply(block)
    }

    fun contact(block: Contact.() -> Unit) {
        contact = Contact().apply(block)
    }

    fun additionalInfo(block: AdditionalInfo.() -> Unit) {
        additionalInfo = AdditionalInfo().apply(block)
    }

    @Serializable
    data class Meta(
        var version: String = "4.0",
        var rev: LocalDateTime = currentDate(),
        var uid: String = UUID.randomUUID().toString(),
    )

    @Serializable
    data class Bio(
        var title: String? = null,
        var fullName: String? = null,
        var givenName: String? = null,
        var familyName: String? = null,
        var nickname: String? = null,
        var honorific: String? = null,
        var birthday: LocalDate? = null,
    )

    @Serializable
    data class Address(
        var street: String? = null,
        var postalCode: String? = null,
        var city: String? = null,
        var state: String? = null,
        var country: String? = null,
    )

    @Serializable
    data class Organization(
        var name: String? = null,
        var branch: String? = null,
        var division: String? = null,
        var anniversary: LocalDate? = null,
    )

    @Serializable
    data class Contact(
        var email: String? = null,
        var cellPhone: String? = null,
    )

    @Serializable
    data class AdditionalInfo(
        var githubUrl: String? = null,
        var gitlabUrl: String? = null,
        var atlasUrl: String? = null,
    )

    fun toVCF(): String {
        return StringBuilder().run {
            appendLine("BEGIN:VCARD")
            appendLine("VERSION:${meta.version}")

            // Bio
            bio?.title?.let { appendLine("TITLE:$it") }
            bio?.fullName?.let { appendLine("FN:$it") }
            bio?.nickname?.let { appendLine("NICKNAME:$it") }
            appendLine("N:${bio?.familyName ?: ""};${bio?.givenName ?: ""};;${bio?.honorific ?: ""};")
            bio?.birthday?.let { appendLine("BDAY:${toIso(it)}") }

            // Organization
            organization?.let { org ->
                appendLine("ORG:${org.name ?: ""};${org.branch ?: ""};${org.division ?: ""}")
                org.anniversary?.let {
                    appendLine("ANNIVERSARY:${toIso(it)}")
                }
            }

            // Address
            address?.let { addr ->
                append("ADR:;;${addr.street ?: ""};${addr.city ?: ""};")
                append("${addr.state ?: ""};${addr.postalCode ?: ""};${addr.country ?: ""}")
                appendLine()
            }

            // Contact
            contact?.email?.let { appendLine("EMAIL:$it") }
            contact?.cellPhone?.let { appendLine("TEL;TYPE=WORK,CELL:$it") }

            // Additional Info
            additionalInfo?.githubUrl?.let { appendLine("URL;type=GitHub:$it") }
            additionalInfo?.gitlabUrl?.let { appendLine("URL;type=GitLab:$it") }
            additionalInfo?.atlasUrl?.let { appendLine("URL;type=Atlas:$it") }

            appendLine("REV:${toIso(meta.rev)}")
            appendLine("UID:${meta.uid}")
            appendLine("END:VCARD")
        }.toString().trim()
    }

    companion object {
        fun vcard(block: VCard.() -> Unit): VCard {
            return VCard().apply(block)
        }

        fun currentDate(): LocalDateTime {
            val tz = TimeZone.currentSystemDefault()
            return Clock.System.now().toLocalDateTime(tz)
        }

        fun toIso(date: LocalDate): String {
            return LocalDate.Formats.ISO.format(date)
        }

        fun toIso(dateTime: LocalDateTime): String {
            return LocalDateTime.Formats.ISO.format(dateTime)
        }

        fun fromIsoDate(date: String): LocalDate {
            return LocalDate.Formats.ISO.parse(date)
        }

        fun fromIsoDateTime(dateTime: String): LocalDateTime {
            return LocalDateTime.Formats.ISO.parse(dateTime)
        }
    }
}