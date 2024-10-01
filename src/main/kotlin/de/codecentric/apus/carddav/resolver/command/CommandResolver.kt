package de.codecentric.apus.carddav.resolver.command

import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.request.RequestMethod
import de.codecentric.apus.carddav.response.MultiStatusResponse

abstract class CommandResolver(val method: RequestMethod) {

    open fun supports(context: RequestContext): Boolean {
        return method == context.command!!.method
    }

    abstract fun resolveLocations(context: RequestContext): Set<String>

    open fun updateResponse(context: RequestContext, response: MultiStatusResponse): MultiStatusResponse {
        return response
    }
}