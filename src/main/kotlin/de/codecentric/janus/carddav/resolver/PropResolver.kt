package de.codecentric.janus.carddav.resolver

import de.codecentric.janus.Namespace
import org.redundent.kotlin.xml.Node

abstract class PropResolver(val propName: String, val namespace: Namespace = Namespace.DAV) {

    open fun supports(resolverContext: ResolverContext): Boolean {
        return propName == resolverContext.propName && namespace == resolverContext.namespace
    }

    abstract fun resolve(context: ResolverContext): Node
}