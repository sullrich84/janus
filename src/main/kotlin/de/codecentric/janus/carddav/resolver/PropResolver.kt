package de.codecentric.janus.carddav.resolver

import de.codecentric.janus.Namespace
import de.codecentric.janus.carddav.request.CardDavRequestContext
import org.redundent.kotlin.xml.Node

/**
 * Resolves a property for a given request context.
 *
 * @param propName the name of the property
 * @param namespace the namespace of the property
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
abstract class PropResolver(val propName: String, val namespace: Namespace = Namespace.DAV) {

    /**
     * Checks if this resolver supports the given request context
     * by comparing the property name and the default namespace.
     */
    open fun supports(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Boolean {
        return propName == resolverContext.propName && namespace == resolverContext.namespace
    }

    abstract fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node
}