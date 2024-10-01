package de.codecentric.apus.carddav.resolver.prop

import de.codecentric.apus.carddav.Namespace
import de.codecentric.apus.carddav.request.RequestContext
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
abstract class PropResolver(open val propName: String, open val namespace: Namespace = Namespace.DAV) {

    /**
     * Checks if this resolver supports the given request context
     * by comparing the property name and the default namespace.
     */
    open fun supports(resolverContext: ResolverContext, requestContext: RequestContext): Boolean {
        return propName == resolverContext.propName && namespace == resolverContext.namespace
    }

    /**
     * Resolves the property for the given request context.
     */
    abstract fun resolve(resolverContext: ResolverContext, requestContext: RequestContext): Node
}