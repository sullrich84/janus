package de.codecentric.janus.carddav

import de.codecentric.janus.Namespace
import de.codecentric.janus.carddav.prop.PropResolver
import de.codecentric.janus.carddav.request.PropFindRequest
import de.codecentric.janus.carddav.response.MultiStatusResponse
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

/**
 * Service class for resolving CardDAV properties.
 *
 * @param resolvers The list of available property resolvers in ApplicationContext

 * @author Sebastian Ullrich
 * @since 1.0.0
 */
@Component
class CardDavService(private val resolvers: List<PropResolver>) {

    fun resolve(href: String, propFindRequest: PropFindRequest): MultiStatusResponse {

        val okProps = mutableMapOf<Node, Namespace>()
        val notFoundProps = mutableMapOf<Node, Namespace>()

        propFindRequest.props.forEach { (propName, namespace) ->
            val resolver = resolvers.firstOrNull { it.supports(propName, namespace) }

            if (resolver != null) {
                okProps[resolver.resolve()] = namespace
            } else {
                val prefixedPropName = namespace.appendPrefix(propName)
                notFoundProps[xml(prefixedPropName)] = namespace
            }
        }

        return MultiStatusResponse(href, okProps, notFoundProps)
    }
}