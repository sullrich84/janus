package de.codecentric.janus.vcard

import de.codecentric.janus.vcard.VCardMediaType.VCARD_MEDIA_TYPE
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets.UTF_8
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * This class is responsible for converting {@link VCard} objects to the VCARD format.
 * It implements the {@link HttpMessageConverter} interface for {@link VCard} type.
 *
 * @author Sebastian Ullrich <sebastian.ullrich@codecentric.de>
 * @since 1.0.0
 */
@Component
class VCardMessageConverter : HttpMessageConverter<VCard> {

    companion object {
        val BDAY_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val TZ_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("XXX")
        val REV_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    }

    override fun getSupportedMediaTypes(): List<MediaType> {
        return listOf(VCARD_MEDIA_TYPE)
    }

    override fun canWrite(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return mediaType == VCARD_MEDIA_TYPE && VCard::class.java == clazz
    }

    override fun write(vCard: VCard, contentType: MediaType?, outputMessage: HttpOutputMessage) {
        val body = vCard.run {
            val timeZoneDate = OffsetDateTime.now(timeZone)

            buildString {
                appendLine("BEGIN:VCARD")
                appendLine("VERSION:3.0")
                appendLine("N:$familyName;$givenName;;;")
                appendLine("FN:$givenName $familyName")
                // ADR:
                appendLine("BDAY:${birthDate.format(BDAY_FORMATTER)}")
                appendLine("EMAIL:$email")
                // GEO:
                // KEY:
                // LABEL:
                appendLine("LANG:$language")
                // NOTE:
                appendLine("ORG:$organization")
                appendLine("REV:${revision.format(REV_FORMATTER)}")
                appendLine("ROLE:$role")
                appendLine("TEL:$phone")
                appendLine("TITLE:$title")
                appendLine("TZ:${timeZoneDate.format(TZ_FORMATTER)}")
                appendLine("UID:$uid")
                appendLine("URL:$url")
                appendLine("END:VCARD")
            }
        }

        val bodyByteArray = body.toByteArray(UTF_8)
        outputMessage.body.write(bodyByteArray)
    }

    override fun canRead(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return false // Read currently not supported
    }

    override fun read(clazz: Class<out VCard>, inputMessage: HttpInputMessage): VCard {
        throw UnsupportedOperationException("Reading vCards not supported")
    }
}
