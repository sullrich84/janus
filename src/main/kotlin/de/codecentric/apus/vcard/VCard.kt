package de.codecentric.apus.vcard

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable
import java.util.*

/**
 * Data class representing a vCard.
 *
 * @param uid Unique identifier of the vCard.
 * @param givenName Given name of the person.
 * @param familyName Family name of the person.
 * @param birthDate Birthdate of the person.
 * @param email Email address of the person.
 * @param language Language of the vCard.
 * @param organization Organization of the person.
 * @param role Role of the person.
 * @param phone Phone number of the person.
 * @param title Title of the person.
 * @param timeZone Time zone of the person.
 * @param revision Date and time of the last revision of the vCard.
 * @param url URL of the person.
 *
 * @author Sebastian Ullrich <sebastian.ullrich@codecentric.de>
 * @since 1.0.0
 */
@Serializable
data class VCard(
    val uid: String = UUID.randomUUID().toString(),
    val givenName: String,
    val familyName: String,
    val birthDate: LocalDate,
    val email: String,
    val language: String,
    val organization: String,
    val role: String,
    val phone: String,
    val title: String? = null,
    val timeZone: TimeZone = TimeZone.currentSystemDefault(),
    val revision: LocalDateTime = Clock.System.now().toLocalDateTime(timeZone),
    val url: String,
)
