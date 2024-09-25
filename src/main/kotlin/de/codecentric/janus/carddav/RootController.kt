package de.codecentric.janus.carddav

import de.codecentric.janus.carddav.request.PropFindRequest
import de.codecentric.janus.carddav.response.prop.CurrentUserPrincipal
import de.codecentric.janus.carddav.response.MultiStatusResponse
import de.codecentric.janus.carddav.response.Response
import de.codecentric.janus.carddav.response.PropStatus
import de.codecentric.janus.carddav.response.prop.PrincipalUrl
import de.codecentric.janus.carddav.response.prop.ResourceType
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.TEXT_XML_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod


/**
 * Controller class for handling requests related to vCards.
 * This class defines an endpoint for retrieving a vCard in the VCARD media type format.
 *
 * @author Sebastian Ullrich <sebastian.ullrich@codecentric.de>
 * @since 1.0.0
 */
@Controller
class RootController(val propService: PropService) {

    @RequestMapping("/codecentric", method = [RequestMethod.OPTIONS])
    fun handleCodecentricOptionsRequest() {

    }

    @RequestMapping("/", produces = [TEXT_XML_VALUE])
    fun handleRootRequest(
        request: HttpServletRequest,
        @RequestBody propFindRequest: PropFindRequest,
    ): ResponseEntity<MultiStatusResponse> {

        propService.resolve(propFindRequest)

        val response = MultiStatusResponse(
            response = Response(
                href = "/",
                propStatus =
            )
        )

        return ResponseEntity
            .status(HttpStatus.MULTI_STATUS)
            .header("DAV", "1, 2, 3, calendar-access, addressbook, extended-mkcol")
            .body(response)
    }
}
