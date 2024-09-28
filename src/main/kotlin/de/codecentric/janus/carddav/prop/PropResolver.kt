package de.codecentric.janus.carddav.prop

import de.codecentric.janus.Namespace
import org.redundent.kotlin.xml.Node

abstract class PropResolver(val propName: String, val namespace: Namespace = Namespace.DAV) {

    open fun supports(propName: String, namespace: Namespace, context: ResolverContext): Boolean {
        return propName == this.propName && namespace == this.namespace
    }

    abstract fun resolve(context: ResolverContext): Node
}