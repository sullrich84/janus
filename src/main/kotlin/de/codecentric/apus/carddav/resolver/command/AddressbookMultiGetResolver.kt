package de.codecentric.apus.carddav.resolver.command

import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.request.RequestMethod.*
import de.codecentric.apus.carddav.response.MultiStatusResponse
import org.springframework.stereotype.Component

@Component
class AddressbookMultiGetResolver : CommandResolver(ADDRESSBOOK_MULTIGET) {

    override fun resolveLocations(context: RequestContext): Set<String> {
        // All vcards that have been requested within the request
        return context.command!!.hrefs.toSet()
    }

    override fun updateResponse(context: RequestContext, response: MultiStatusResponse): MultiStatusResponse {
        return response
    }
}