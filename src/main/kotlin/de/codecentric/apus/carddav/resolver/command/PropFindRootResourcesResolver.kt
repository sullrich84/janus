package de.codecentric.apus.carddav.resolver.command

import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.request.RequestMethod.*
import org.springframework.stereotype.Component

@Component
class PropFindRootResourcesResolver : CommandResolver(PROPFIND) {

    override fun supports(context: RequestContext): Boolean {
        return super.supports(context) && context.requestUri.path == "/"
    }

    override fun resolveLocations(context: RequestContext): Set<String> {
        return setOf("/")
    }
}