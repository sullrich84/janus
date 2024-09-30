@file:Suppress("SpellCheckingInspection")

package de.codecentric.janus.vcard

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import org.springframework.stereotype.Component

/**
 * This class is responsible for converting {@link VCard} objects to the VCARD format.
 *
 * @author Sebastian Ullrich <sebastian.ullrich@codecentric.de>
 * @since 1.0.0
 */
@Component
class VCardWriter {

    @OptIn(FormatStringsInDatetimeFormats::class)
    val BDAY_FORMATTER: DateTimeFormat<LocalDate> = LocalDate
        .Format { byUnicodePattern("yyyy-MM-dd") }

    @OptIn(FormatStringsInDatetimeFormats::class)
    val REV_FORMATTER: DateTimeFormat<LocalDateTime> = LocalDateTime
        .Format { byUnicodePattern("yyyy-MM-dd'T'HH:mm:ss") }

    fun write(vCard: VCard): Array<String> {
        return vCard.run {
            arrayOf(
                "BEGIN:VCARD",
                "VERSION:3.0",
                "UID:$uid",
                "REV:${REV_FORMATTER.format(revision)}",
                "N:$familyName;$givenName;;;",
                "FN:$givenName $familyName",
                "BDAY:${BDAY_FORMATTER.format(birthDate)}",
                "EMAIL:$email",
                "LANG:$language",
                "ORG:$organization",
                "ROLE:$role",
                "TEL:$phone",
                "TITLE:$title",
                "URL:$url",
                "END:VCARD"
            )
        }
    }
}
