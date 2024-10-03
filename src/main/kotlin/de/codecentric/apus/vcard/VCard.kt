@file:Suppress("MemberVisibilityCanBePrivate")

package de.codecentric.apus.vcard

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class VCard {
    var meta: Meta = Meta()
    var bio: Bio? = null
    var photo: Photo? = null
    var contact: Contact? = null
    var additionalInfo: AdditionalInfo? = null
    var organization: Organization? = null
    var address: Address? = null

    val uid: String
        get() = meta.uid

    val updatedAt: LocalDateTime
        get() = meta.rev

    fun meta(block: Meta.() -> Unit) {
        meta = Meta().apply(block)
    }

    fun bio(block: Bio.() -> Unit) {
        bio = Bio().apply(block)
    }

    fun photo(block: Photo.() -> Unit) {
        photo = Photo().apply(block)
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

    @Serializable
    enum class ImageType {
        JPEG, PNG
    }

    @Serializable
    data class Photo(
        var data: String? = null,
        var type: ImageType? = null,
    )

    fun toVCF(): String {
        return StringBuilder().run {
            meta.run {
                appendLine("BEGIN:VCARD")
                appendLine("VERSION:$version")
                appendLine("UID:$uid")
                appendLine("REV:${toIso(rev)}")
            }

            // Bio
            bio?.run {
                title?.let { appendLine("TITLE;CHARSET=UTF-8:$it") }
                appendLine("FN;CHARSET=UTF-8:${honorific.plus(" ")}${givenName ?: ""} ${familyName ?: ""}")
                appendLine("N;CHARSET=UTF-8:${familyName ?: ""};${givenName ?: ""};;${honorific ?: ""};")
                nickname?.let { appendLine("NICKNAME;CHARSET=UTF-8:$it") }
                birthday?.let { appendLine("BDAY:${toIso(it)}") }
            }

            // Organization
            organization?.run {
                appendLine("ORG;CHARSET=UTF-8:${name ?: ""};${branch ?: ""};${division ?: ""}")
                anniversary?.let { appendLine("ANNIVERSARY:${toIso(it)}") }
            }

            // Address
            address?.run {
                append("ADR;TYPE=WORK:;;${street ?: ""};${city ?: ""};")
                append("${state ?: ""};${postalCode ?: ""};${country ?: ""}")
                appendLine()
            }

            // Contact
            contact?.run {
                email?.let { appendLine("EMAIL:$it") }
                cellPhone?.let { appendLine("TEL;TYPE=WORK,CELL:$it") }
            }

            // Additional Info
            additionalInfo?.run {
                githubUrl?.let { appendLine("URL;type=GitHub:$it") }
                gitlabUrl?.let { appendLine("URL;type=GitLab:$it") }
                atlasUrl?.let { appendLine("URL;type=Atlas:$it") }
            }

            // Photo
            photo?.run {
                appendLine("PHOTO;TYPE=$type;ENCODING=base64:$data")
            }

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