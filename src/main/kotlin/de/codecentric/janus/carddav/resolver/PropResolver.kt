package de.codecentric.janus.carddav.resolver

import de.codecentric.janus.Namespace
import de.codecentric.janus.carddav.request.CardDavRequestContext
import org.redundent.kotlin.xml.Node

abstract class PropResolver(val propName: String, val namespace: Namespace = Namespace.DAV) {

    open fun supports(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Boolean {
        return propName == resolverContext.propName && namespace == resolverContext.namespace
    }

    abstract fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node
}