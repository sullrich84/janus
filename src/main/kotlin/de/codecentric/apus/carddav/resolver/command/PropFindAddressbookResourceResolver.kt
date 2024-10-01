package de.codecentric.apus.carddav.resolver.command

import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.request.RequestMethod.*
import de.codecentric.apus.carddav.response.MultiStatusResponse
import org.springframework.stereotype.Component

@Component
class PropFindAddressbookResourceResolver : CommandResolver(PROPFIND) {

    override fun supports(context: RequestContext): Boolean {
        return super.supports(context) && context.requestUri.path.endsWith("/addressbook")
    }

    override fun resolveLocations(context: RequestContext): Set<String> {
              return setOf(context.requestUri.path.plus("/"))
    }

    override fun updateResponse(context: RequestContext, response: MultiStatusResponse): MultiStatusResponse {
        return response
    }
}