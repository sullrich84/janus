package de.codecentric.apus.carddav

import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.resolver.command.CommandResolver
import de.codecentric.apus.carddav.resolver.prop.PropResolver
import de.codecentric.apus.carddav.resolver.prop.ResolverContext
import de.codecentric.apus.carddav.response.MultiStatusResponse
import de.codecentric.apus.carddav.response.StatusResponse
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

/**
 * Service class for resolving CardDAV properties.
 *
 * @param propResolver The list of available property resolvers in ApplicationContext
 * @param cmdResolver The list of available command resolvers in ApplicationContext
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
@Component
class CardDavService(private val propResolver: List<PropResolver>, private val cmdResolver: List<CommandResolver>) {

    /**
     * Identifies and invokes the matching [CommandResolver] for the given [RequestContext].
     *
     * @param context The request context to be resolved
     */
    fun resolve(context: RequestContext): MultiStatusResponse {
        val resolver = cmdResolver.firstOrNull { resolver -> resolver.supports(context) }
        checkNotNull(resolver) { "No matching command resolver found for context: $context" }

        val responses = resolver.resolveLocations(context)
            .map { resolveStatusResponse(it, context) }
            .toList()

        val response = MultiStatusResponse(responses = responses)
        return resolver.updateResponse(context, response)
    }

    /**
     * Resolves the properties for the given location and request context.
     *
     * @param location The location to resolve the properties for
     * @param context The request context to resolve the properties for
     */
    private fun resolveStatusResponse(location: String, context: RequestContext): StatusResponse {
        val okProps = mutableListOf<Node>()
        val notFoundProps = mutableListOf<Node>()
        val namespaces = mutableSetOf<Namespace>()

        context.command?.props?.forEach { prop ->
            val resolverContext = ResolverContext(prop.name, prop.namespace, location)
            val resolver = propResolver.firstOrNull { it.supports(resolverContext, context) }

            namespaces.add(prop.namespace)

            if (resolver != null) {
                val node = resolver.resolve(resolverContext, context)
                okProps.add(node)
            } else {
                val prefixedPropName = prop.namespace.appendPrefix(prop.name)
                val node = xml(prefixedPropName)
                notFoundProps.add(node)
            }
        }

        return StatusResponse(location, okProps, notFoundProps)
    }
}
