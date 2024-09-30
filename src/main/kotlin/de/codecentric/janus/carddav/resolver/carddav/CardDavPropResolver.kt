package de.codecentric.janus.carddav.resolver.carddav

import de.codecentric.janus.Namespace.CARDDAV
import de.codecentric.janus.carddav.request.CardDavRequestContext
import de.codecentric.janus.carddav.resolver.PropResolver
import de.codecentric.janus.carddav.resolver.ResolverContext
import org.redundent.kotlin.xml.Node

/**
 * Resolves a property for a given request context.
 *
 * @param propName the name of the carddav property
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
abstract class CardDavPropResolver(override val propName: String) : PropResolver(propName, CARDDAV) {

    /**
     * Resolves the property for the given request context.
     */
    abstract override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node
}