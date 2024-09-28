package de.codecentric.janus.carddav

import de.codecentric.janus.Namespace
import de.codecentric.janus.carddav.prop.PropResolver
import de.codecentric.janus.carddav.prop.ResolverContext
import de.codecentric.janus.carddav.request.PropFindRequest
import de.codecentric.janus.carddav.response.MultiStatusResponse
import de.codecentric.janus.carddav.response.StatusResponse
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

    fun resolve(hrefs: List<String>, propFindRequest: PropFindRequest, principal: String? = null): MultiStatusResponse {
        val responses = hrefs.map { resolveStatusResponse(it, propFindRequest, principal) }.toList()
        return MultiStatusResponse(responses = responses)
    }

    fun resolveStatusResponse(href: String, propFindRequest: PropFindRequest, principal: String?): StatusResponse {
        val okProps = mutableMapOf<Node, Namespace>()
        val notFoundProps = mutableMapOf<Node, Namespace>()

        propFindRequest.props.forEach { (propName, namespace) ->
            val context = ResolverContext(href, principal)
            val resolver = resolvers.firstOrNull { it.supports(propName, namespace, context) }

            if (resolver != null) {
                okProps[resolver.resolve(context)] = namespace
            } else {
                val prefixedPropName = namespace.appendPrefix(propName)
                notFoundProps[xml(prefixedPropName)] = namespace
            }
        }

        return StatusResponse(href, okProps, notFoundProps)
    }
}