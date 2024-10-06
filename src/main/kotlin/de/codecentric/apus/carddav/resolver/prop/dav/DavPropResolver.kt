package de.codecentric.apus.carddav.resolver.prop.dav

import de.codecentric.apus.carddav.Namespace.DAV
import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.resolver.prop.PropResolver
import de.codecentric.apus.carddav.resolver.prop.ResolverContext
import org.redundent.kotlin.xml.Node

/**
 * Resolves a property for a given WebDAV request context.
 *
 * @param propName the name of the dav property
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
abstract class DavPropResolver(override val propName: String) : PropResolver(propName, DAV) {

    /**
     * Resolves the property for the given request context.
     */
    abstract override fun resolve(resolverContext: ResolverContext, requestContext: RequestContext): Node
}
