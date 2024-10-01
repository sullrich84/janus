@file:Suppress("SpellCheckingInspection")

package de.codecentric.apus.vcard

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.springframework.stereotype.Component

/**
 * This class is responsible for converting {@link VCard} objects to the VCARD format.
 *
 * @author Sebastian Ullrich <sebastian.ullrich@codecentric.de>
 * @since 1.0.0
 */
@Component
class VCardWriter {

    fun write(vCard: VCard): Array<String> {
        val bday = LocalDate.Formats.ISO.format(vCard.birthDate)
        val rev = LocalDateTime.Formats.ISO.format(vCard.revision)

        return vCard.run {
            arrayOf(
                "BEGIN:VCARD",
                "VERSION:3.0",
                "UID:$uid",
                "REV:$rev",
                "N:$familyName;$givenName;;;",
                "FN:$givenName $familyName",
                "BDAY:$bday",
                "EMAIL:$email",
                "LANG:$language",
                "ORG:$organization",
                "ROLE:$role",
                "TEL:$phone",
                "TITLE:$title",
                "URL:$url",
                "END:VCARD",
            )
        }
    }
}
