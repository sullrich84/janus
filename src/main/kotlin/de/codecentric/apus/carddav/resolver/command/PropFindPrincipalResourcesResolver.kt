package de.codecentric.apus.carddav.resolver.command

import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.request.RequestMethod.*
import de.codecentric.apus.carddav.response.MultiStatusResponse
import org.springframework.stereotype.Component

@Component
class PropFindPrincipalResourcesResolver : CommandResolver(PROPFIND) {

    override fun supports(context: RequestContext): Boolean {
        return super.supports(context) && context.requestUri.path == "/${context.principal}/"
    }

    override fun resolveLocations(context: RequestContext): Set<String> {
        val locations = mutableSetOf("/${context.principal}/")
        if (context.depth > 0) locations.add("/${context.principal}/addressbook/")
        return locations
    }

    override fun updateResponse(context: RequestContext, response: MultiStatusResponse): MultiStatusResponse {
        return response
    }
}