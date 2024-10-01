package de.codecentric.apus.carddav.resolver.command

import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.request.RequestMethod.*
import de.codecentric.apus.carddav.response.MultiStatusResponse
import de.codecentric.apus.vcard.VCardService
import org.springframework.stereotype.Component

@Component
class PropFindAddressbookResourcesResolver(private val service: VCardService) : CommandResolver(PROPFIND) {

    override fun supports(context: RequestContext): Boolean {
        return super.supports(context) && context.requestUri.path.endsWith("/addressbook/")
    }

    override fun resolveLocations(context: RequestContext): Set<String> {
        val locations = mutableSetOf(context.requestUri.path)
        if (context.depth == 0) return locations

        val vCardLocations = service.getAll()
            .map { context.requestUri.path.plus("${it.uid}.vcf") }
            .toSet()

        return setOf(context.requestUri.path).plus(vCardLocations)
    }

    override fun updateResponse(context: RequestContext, response: MultiStatusResponse): MultiStatusResponse {
        return response
    }
}