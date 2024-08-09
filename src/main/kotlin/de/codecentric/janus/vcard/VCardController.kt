package de.codecentric.janus.vcard

import de.codecentric.janus.vcard.VCardMediaType.VCARD_MEDIA_TYPE_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.time.LocalDate
import java.time.ZoneOffset


/**
 * Controller class for handling requests related to vCards.
 * This class defines an endpoint for retrieving a vCard in the VCARD media type format.
 *
 * @author Sebastian Ullrich <sebastian.ullrich@codecentric.de>
 * @since 1.0.0
 */
@Controller
class VCardController {

    @GetMapping("/vcards", produces = [VCARD_MEDIA_TYPE_VALUE])
    fun getVCards(): ResponseEntity<VCard> {
        val vCard = VCard(
            givenName = "Sebastian",
            familyName = "Ullrich",
            birthDate = LocalDate.of(1984, 3, 29),
            email = "sebastian.ullrich@codecentric.de",
            language = "de-DE",
            organization = "codecentric AG",
            role = "Senior Consultant & Developer",
            phone = "+4915112345678",
            title = "M.Sc.",
            timeZone = ZoneOffset.ofHours(-2),
            url = "https://ullrich.tech",
        )

        return ResponseEntity.ok().body(vCard)
    }
}
