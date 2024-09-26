package de.codecentric.janus.carddav

import de.codecentric.janus.carddav.request.PropFindRequest
import de.codecentric.janus.carddav.resolver.Prop
import de.codecentric.janus.carddav.resolver.PropResolver
import de.codecentric.janus.carddav.response.MultiStatusResponse
import org.springframework.stereotype.Component

/**
 * Service class for resolving CardDAV properties.
 *
 * @param resolvers The list of available property resolvers in ApplicationContext

 * @author Sebastian Ullrich
 * @since 1.0.0
 */
@Component
class CardDavService(val resolvers: List<PropResolver<*>>) {

    fun resolve(href: String, propFindRequest: PropFindRequest): MultiStatusResponse {
        val listOk = mutableListOf<Prop>()
        val listNotFound = mutableListOf<Prop>()

        propFindRequest.props.forEach { (propName, nsAlias) ->
            val resolver = resolvers.firstOrNull { it.supports(propName) }

            // Resolve namespace alias to correct alias
            val namespace = propFindRequest.nsAliases.filterKeys { it == nsAlias }.values.first()

            if (resolver == null) {
                val prop = Prop(propName, namespace)
                listNotFound.add(prop)
                return@forEach
            }

            val prop = resolver.resolve()
            listOk.add(prop)
        }

        return MultiStatusResponse(
            href = href,
            nsAliases = mapOf(),
            ok = listOk,
            notFound = listNotFound
        )
    }
}