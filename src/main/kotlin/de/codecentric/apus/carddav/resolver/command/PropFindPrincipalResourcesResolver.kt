package de.codecentric.apus.carddav.resolver.command

import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.request.RequestMethod.*
import org.springframework.stereotype.Component

@Component
class PropFindPrincipalResourcesResolver : CommandResolver(PROPFIND) {

    override fun supports(context: RequestContext): Boolean {
        return super.supports(context) && context.requestUri.path == "/${context.principal}/"
    }

    override fun resolveLocations(context: RequestContext): Set<String> {
        return setOfNotNull(
            "/${context.principal}/",
            "/${context.principal}/addressbook/".takeIf { context.depth > 0 }
        )
    }
}