package de.codecentric.janus.carddav

import de.codecentric.janus.carddav.request.CardDavRequestContext
import de.codecentric.janus.carddav.request.WebDavRequest
import de.codecentric.janus.carddav.response.MultiStatusResponse
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.MULTI_STATUS
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.TEXT_XML
import org.springframework.http.MediaType.TEXT_XML_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.OPTIONS
import org.springframework.web.server.ResponseStatusException


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
    fun handlePropFindRequest(
        @RequestBody webDavRequest: WebDavRequest,
        @RequestHeader(defaultValue = "0") depth: Int,
        @RequestHeader(defaultValue = "f") brief: String,
    ): ResponseEntity<MultiStatusResponse> {
        val cardDavRequestContext = CardDavRequestContext(
            depth = 0,
            brief = brief == "t",
            locations = listOf("/"),
            webDavRequest = webDavRequest,
        )

        val response = service.resolve(context = cardDavRequestContext)

        return ResponseEntity.status(MULTI_STATUS).contentType(TEXT_XML)
            // TODO: Check if we only need
            //  DAV: 1, 3, addressbook
            //  Allow: GET, HEAD, OPTIONS, REPORT
            .header("DAV", "1, 2, 3, calendar-access, addressbook, extended-mkcol").body(response)
    }

    @RequestMapping("/**", method = [OPTIONS])
    fun handlePrincipalOptionsRequest(): ResponseEntity<Void> {
        return ResponseEntity.status(OK).allow(
                // TODO: Check if we only need
                //  DAV: 1, 3, addressbook
                //  Allow: GET, HEAD, OPTIONS, REPORT
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
            ).header("DAV", "1, 2, 3, calendar-access, addressbook, extended-mkcol").build()
    }

    @RequestMapping("/{principal}/", method = [], produces = [TEXT_XML_VALUE])
    fun handlePrincipalPropFindRequest(
        @PathVariable principal: String,
        @RequestBody webDavRequest: WebDavRequest,
        @RequestHeader(defaultValue = "0") depth: Int,
        @RequestHeader(defaultValue = "f") brief: String,
    ): ResponseEntity<MultiStatusResponse> {
        val cardDavRequestContext = CardDavRequestContext(
            depth = depth,
            brief = brief == "t",
            locations = listOf("/$principal/"),
            webDavRequest = webDavRequest,
            principal = principal,
        )

        val response = service.resolve(cardDavRequestContext)

        return ResponseEntity.status(MULTI_STATUS).contentType(TEXT_XML)
            // TODO: Check if we only need
            //  DAV: 1, 3, addressbook
            //  Allow: GET, HEAD, OPTIONS, REPORT
            .header("DAV", "1, 2, 3, calendar-access, addressbook, extended-mkcol").body(response)
    }

    @RequestMapping("/{principal}/addressbook", produces = [TEXT_XML_VALUE])
    fun handlePrincipalAddressbookPropFindRequest(
        @PathVariable principal: String,
        @RequestBody webDavRequest: WebDavRequest,
        @RequestHeader(defaultValue = "0") depth: Int,
        @RequestHeader(defaultValue = "f") brief: String,
    ): ResponseEntity<MultiStatusResponse> {
        val cardDavRequestContext = CardDavRequestContext(
            depth = depth,
            brief = brief == "t",
            locations = listOf("/$principal/addressbook/"),
            webDavRequest = webDavRequest,
            principal = principal,
        )

        val response = service.resolve(cardDavRequestContext)

        return ResponseEntity.status(MULTI_STATUS).contentType(TEXT_XML)
            // TODO: Check if we only need
            //  DAV: 1, 3, addressbook
            //  Allow: GET, HEAD, OPTIONS, REPORT
            .header("DAV", "1, 2, 3, calendar-access, addressbook, extended-mkcol").body(response)
    }

    @RequestMapping("/{principal}/addressbook/", produces = [TEXT_XML_VALUE])
    fun handlePrincipalAddressbookPathPropFindRequest(
        @PathVariable principal: String,
        @RequestBody webDavRequest: WebDavRequest,
        @RequestHeader(defaultValue = "0") depth: Int,
        @RequestHeader(defaultValue = "f") brief: String,
    ): ResponseEntity<MultiStatusResponse> {
        val cardDavRequestContext = CardDavRequestContext(
            depth = depth,
            brief = brief == "t",
            locations = listOf("/$principal/addressbook/"),
            webDavRequest = webDavRequest,
            principal = principal,
        )

        val response = service.resolve(cardDavRequestContext)

        return ResponseEntity.status(MULTI_STATUS).contentType(TEXT_XML)
            // TODO: Check if we only need
            //  DAV: 1, 3, addressbook
            //  Allow: GET, HEAD, OPTIONS, REPORT
            .header("DAV", "1, 2, 3, calendar-access, addressbook, extended-mkcol").body(response)
    }

    @RequestMapping("/.well-known/carddav")
    fun handleWellKnownCardDavRequest(): ResponseEntity<Void> {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Location", "/").build()
    }
}
