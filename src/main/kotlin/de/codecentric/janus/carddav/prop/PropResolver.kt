package de.codecentric.janus.carddav.prop

import de.codecentric.janus.Namespace
import de.codecentric.janus.carddav.request.CardDavContext
import org.redundent.kotlin.xml.Node

abstract class PropResolver(val propName: String, val namespace: Namespace = Namespace.DAV) {

    open fun supports(resolverContext: ResolverContext, cardDavContext: CardDavContext): Boolean {
        return propName == resolverContext.propName && namespace == resolverContext.namespace
    }

    abstract fun resolve(context: ResolverContext): Node
}