package de.codecentric.janus.vcard

import org.springframework.http.MediaType

/**
 * Object for defining the media type for vCards.
 *
 * @author Sebastian Ullrich <sebastian.ullrich@deepsource.de>
 * @since 1.0.0
 */
object VCardMediaType {

    const val VCARD_MEDIA_TYPE_VALUE = "text/vcard"
    val VCARD_MEDIA_TYPE = MediaType.parseMediaType(VCARD_MEDIA_TYPE_VALUE)
}
