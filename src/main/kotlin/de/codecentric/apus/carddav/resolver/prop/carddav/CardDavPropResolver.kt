package de.codecentric.apus.carddav.resolver.prop.carddav

import de.codecentric.apus.carddav.Namespace.CARDDAV
import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.resolver.prop.PropResolver
import de.codecentric.apus.carddav.resolver.prop.ResolverContext
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
    abstract override fun resolve(resolverContext: ResolverContext, requestContext: RequestContext): Node
}