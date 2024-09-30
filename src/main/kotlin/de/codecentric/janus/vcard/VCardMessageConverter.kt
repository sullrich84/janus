package de.codecentric.janus.vcard

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets.UTF_8

/**
 * This class is responsible for converting {@link VCard} objects to the VCARD format.
 * It implements the {@link HttpMessageConverter} interface for {@link VCard} type.
 *
 * @author Sebastian Ullrich <sebastian.ullrich@codecentric.de>
 * @since 1.0.0
 */
@Component
class VCardMessageConverter {

    @Suppress("SpellCheckingInspection")
    @OptIn(FormatStringsInDatetimeFormats::class)
    companion object {
        val BDAY_FORMATTER: DateTimeFormat<LocalDate> = LocalDate
            .Format { byUnicodePattern("yyyy-MM-dd") }
        val REV_FORMATTER: DateTimeFormat<LocalDateTime> = LocalDateTime
            .Format { byUnicodePattern("yyyy-MM-dd'T'HH:mm:ss") }
    }

    fun write(vCard: VCard, contentType: MediaType?, outputMessage: HttpOutputMessage) {
        val body = vCard.run {

            buildString {
                appendLine("BEGIN:VCARD")
                appendLine("VERSION:3.0")
                appendLine("N:$familyName;$givenName;;;")
                appendLine("FN:$givenName $familyName")
                // ADR:
                appendLine("BDAY:${BDAY_FORMATTER.format(birthDate)}")
                appendLine("EMAIL:$email")
                // GEO:
                // KEY:
                // LABEL:
                appendLine("LANG:$language")
                // NOTE:
                appendLine("ORG:$organization")
                appendLine("REV:${REV_FORMATTER.format(revision)}")
                appendLine("ROLE:$role")
                appendLine("TEL:$phone")
                appendLine("TITLE:$title")
                // TZ:
                appendLine("UID:$uid")
                appendLine("URL:$url")
                appendLine("END:VCARD")
            }
        }

        val bodyByteArray = body.toByteArray(UTF_8)
        outputMessage.body.write(bodyByteArray)
    }
}
