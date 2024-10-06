package de.codecentric.apus.carddav

import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.request.WebDavRequest
import de.codecentric.apus.carddav.response.MultiStatusResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.MULTI_STATUS
import org.springframework.http.HttpStatus.OK
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

    @RequestMapping("/")
    fun handlePropFindRequest(
        @RequestBody webDavRequest: WebDavRequest,
        requestContext: RequestContext,
    ): MultiStatusResponseEntity {
        return requestContext.let { ctx ->
            ctx.command = webDavRequest
            MultiStatusResponseEntity(service.resolve(requestContext), MULTI_STATUS)
        }
    }

    @RequestMapping("/**", method = [OPTIONS])
    fun handlePrincipalOptionsRequest(): MultiStatusResponseEntity {
        return MultiStatusResponseEntity(OK)
    }

    @RequestMapping("/{principal}/")
    fun handlePrincipalPropFindRequest(
        @PathVariable principal: String,
        @RequestBody webDavRequest: WebDavRequest,
        requestContext: RequestContext,
    ): MultiStatusResponseEntity {
        return requestContext.let { ctx ->
            ctx.command = webDavRequest
            ctx.principal = principal
            MultiStatusResponseEntity(service.resolve(requestContext), MULTI_STATUS)
        }
    }

    @RequestMapping("/{principal}/addressbook")
    fun handlePrincipalAddressbookPropFindRequest(
        @PathVariable principal: String,
        @RequestBody webDavRequest: WebDavRequest,
        requestContext: RequestContext,
    ): MultiStatusResponseEntity {
        return requestContext.let { ctx ->
            ctx.command = webDavRequest
            ctx.principal = principal
            MultiStatusResponseEntity(service.resolve(requestContext), MULTI_STATUS)
        }
    }

    @RequestMapping("/{principal}/addressbook/")
    fun handlePrincipalAddressbookPathPropFindRequest(
        @PathVariable principal: String,
        @RequestBody webDavRequest: WebDavRequest,
        requestContext: RequestContext,
    ): MultiStatusResponseEntity {
        return requestContext.let { ctx ->
            ctx.command = webDavRequest
            ctx.principal = principal
            MultiStatusResponseEntity(service.resolve(requestContext), MULTI_STATUS)
        }
    }

    @RequestMapping("/.well-known/carddav")
    fun handleWellKnownCardDavRequest(): ResponseEntity<Void> {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
            .header("Location", "/")
            .build()
    }
}
