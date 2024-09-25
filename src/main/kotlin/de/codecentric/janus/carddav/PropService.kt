package de.codecentric.janus.carddav

import de.codecentric.janus.carddav.request.PropFindRequest
import de.codecentric.janus.carddav.resolver.PropResolver
import de.codecentric.janus.carddav.response.PropStatus
import de.codecentric.janus.carddav.response.prop.Prop
import org.springframework.stereotype.Component

@Component
class PropService(val resolvers: List<PropResolver>) {

    fun resolve(propFindRequest: PropFindRequest): List<PropStatus> {
        val list200 = buildPropStatus("HTTP/1.1 200 OK")
        val list404 = buildPropStatus("HTTP/1.1 404 Not Found")

        propFindRequest.prop.keys.forEach { propName ->
            val resolver = resolvers.first { it.supports(propName) }
            if (resolver != null) {

            }

        }


        return emptyList()
    }

    private fun buildPropStatus(status: String): PropStatus {
        return PropStatus(
            props = mutableListOf<Prop>(),
            status = status
        )
    }
}