package de.codecentric.janus.carddav

import de.codecentric.janus.Namespace
import de.codecentric.janus.carddav.request.CardDavRequestContext
import de.codecentric.janus.carddav.resolver.PropResolver
import de.codecentric.janus.carddav.resolver.ResolverContext
import de.codecentric.janus.carddav.response.MultiStatusResponse
import de.codecentric.janus.carddav.response.StatusResponse
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component
import javax.naming.Name

/**
 * Service class for resolving CardDAV properties.
 *
 * @param resolvers The list of available property resolvers in ApplicationContext

 * @author Sebastian Ullrich
 * @since 1.0.0
 */
@Component
class CardDavService(private val resolvers: MutableList<out PropResolver>) {

    fun resolve(cardDavRequestContext: CardDavRequestContext): MultiStatusResponse {
        val responses = cardDavRequestContext.locations.map { resolveStatusResponse(it, cardDavRequestContext) }
        return MultiStatusResponse(responses.toList())
    }

    private fun resolveStatusResponse(location: String, cardDavRequestContext: CardDavRequestContext): StatusResponse {
        val okProps = mutableMapOf<Node, Namespace>()
        val notFoundProps = mutableMapOf<Node, Namespace>()
        val namespaces = mutableSetOf<Namespace>()

        cardDavRequestContext.propFindRequest.props.forEach { (propName, namespace) ->
            val resolverContext = ResolverContext(propName, namespace, location)
            val resolver = resolvers.firstOrNull { it.supports(resolverContext, cardDavRequestContext) }

            namespaces.add(namespace)

            if (resolver != null) {
                val node = resolver.resolve(resolverContext, cardDavRequestContext)
                okProps[node] = namespace
            } else {
                val prefixedPropName = namespace.appendPrefix(propName)
                val node = xml(prefixedPropName)
                notFoundProps[node] = namespace
            }
        }

        return StatusResponse(location, okProps, notFoundProps)
    }
}