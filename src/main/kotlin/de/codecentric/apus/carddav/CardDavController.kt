package de.codecentric.apus.carddav

import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.request.WebDavRequest
import de.codecentric.apus.carddav.response.MultiStatusResponse
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.MULTI_STATUS
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
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

    @RequestMapping("/", produces = ["text/xml; charset=utf-8"])
    fun handlePropFindRequest(
        @RequestBody webDavRequest: WebDavRequest,
        requestContext: RequestContext,
    ): ResponseEntity<MultiStatusResponse> {

        requestContext.command = webDavRequest
        val response = service.resolve(requestContext)

        return ResponseEntity.status(MULTI_STATUS)
            .contentType(MediaType.valueOf("text/xml; charset=utf-8"))
            .header("DAV", "1, 2, 3, addressbook")
            .body(response)
    }

    @RequestMapping("/**", method = [OPTIONS], produces = ["text/xml; charset=utf-8"])
    fun handlePrincipalOptionsRequest(): ResponseEntity<Void> {
        return ResponseEntity.status(OK)
            .allow(
                HttpMethod.GET,
                HttpMethod.OPTIONS,
                HttpMethod.valueOf("PROPFIND"),
                HttpMethod.valueOf("REPORT"),
            )
            .header("DAV", "1, 2, 3, addressbook")
            .build()
    }

    @RequestMapping("/{principal}/", produces = ["text/xml; charset=utf-8"])
    fun handlePrincipalPropFindRequest(
        @PathVariable principal: String,
        @RequestBody webDavRequest: WebDavRequest,
        requestContext: RequestContext,
    ): ResponseEntity<MultiStatusResponse> {

        requestContext.command = webDavRequest
        requestContext.principal = principal
        val response = service.resolve(requestContext)

        return ResponseEntity.status(MULTI_STATUS)
            .contentType(MediaType.valueOf("text/xml; charset=utf-8"))
            .header("DAV", "1, 2, 3, addressbook")
            .body(response)
    }

    @RequestMapping("/{principal}/addressbook", produces = ["text/xml; charset=utf-8"])
    fun handlePrincipalAddressbookPropFindRequest(
        @PathVariable principal: String,
        @RequestBody webDavRequest: WebDavRequest,
        requestContext: RequestContext,
    ): ResponseEntity<MultiStatusResponse> {

        requestContext.command = webDavRequest
        requestContext.principal = principal
        val response = service.resolve(requestContext)

        return ResponseEntity.status(MULTI_STATUS)
            .contentType(MediaType.valueOf("text/xml; charset=utf-8"))
            .header("DAV", "1, 2, 3, addressbook")
            .body(response)
    }

    @RequestMapping("/{principal}/addressbook/", produces = ["text/xml; charset=utf-8"])
    fun handlePrincipalAddressbookPathPropFindRequest(
        @PathVariable principal: String,
        @RequestBody webDavRequest: WebDavRequest,
        requestContext: RequestContext,
    ): ResponseEntity<MultiStatusResponse> {

        requestContext.command = webDavRequest
        requestContext.principal = principal
        val response = service.resolve(requestContext)

        return ResponseEntity.status(MULTI_STATUS)
            .contentType(MediaType.valueOf("text/xml; charset=utf-8"))
            .header("DAV", "1, 2, 3, addressbook")
            .body(response)
    }

    @RequestMapping("/.well-known/carddav", produces = ["text/xml; charset=utf-8"])
    fun handleWellKnownCardDavRequest(): ResponseEntity<Void> {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
            .header("Location", "/")
            .build()
    }
}
