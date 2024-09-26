package de.codecentric.janus.carddav

import de.codecentric.janus.carddav.request.PropFindRequest
import de.codecentric.janus.carddav.response.MultiStatusResponse
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus.MULTI_STATUS
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.TEXT_XML
import org.springframework.http.MediaType.TEXT_XML_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.OPTIONS


/**
 * Controller class for handling requests related to vCards.
 * This class defines an endpoint for retrieving a vCard in the VCARD media type format.
 *
 * @author Sebastian Ullrich <sebastian.ullrich@codecentric.de>
 * @since 1.0.0
 */
@Controller
class CardDavController(val service: CardDavService) {

    @RequestMapping("/", produces = [TEXT_XML_VALUE])
    fun handlePropFindRequest(@RequestBody propFindRequest: PropFindRequest): ResponseEntity<MultiStatusResponse> {
        val response = service.resolve(href = "/", propFindRequest = propFindRequest)
        return ResponseEntity
            .status(MULTI_STATUS)
            .contentType(TEXT_XML)
            .header("DAV", "1, 2, 3, calendar-access, addressbook, extended-mkcol")
            .body(response)
    }

    @RequestMapping("/*/", method = [OPTIONS])
    fun handlePrincipalOptionsRequest(): ResponseEntity<MultiStatusResponse> {
        return ResponseEntity
            .status(OK)
            .allow(
                // TODO: Only PROPFIND and REPORT are used?!
                HttpMethod.DELETE,
                HttpMethod.GET,
                HttpMethod.HEAD,
                HttpMethod.OPTIONS,
                HttpMethod.POST,
                HttpMethod.PUT,
                HttpMethod.valueOf("MKCALENDAR"),
                HttpMethod.valueOf("MKCOL"),
                HttpMethod.valueOf("MOVE"),
                HttpMethod.valueOf("PROPFIND"),
                HttpMethod.valueOf("PROPPATCH"),
                HttpMethod.valueOf("REPORT"),
            )
            .header("DAV", "1, 2, 3, calendar-access, addressbook, extended-mkcol")
            .build()
    }

    @RequestMapping("/{principal}/", produces = [TEXT_XML_VALUE])
    fun handlePrincipalPropFindRequest(
        @PathVariable principal: String,
        @RequestBody propFindRequest: PropFindRequest,
    ): ResponseEntity<MultiStatusResponse> {
        val response = service.resolve(href = "/$principal/", propFindRequest = propFindRequest)
        return ResponseEntity
            .status(MULTI_STATUS)
            .contentType(TEXT_XML)
            .header("DAV", "1, 2, 3, calendar-access, addressbook, extended-mkcol")
            .body(response)
    }
}
