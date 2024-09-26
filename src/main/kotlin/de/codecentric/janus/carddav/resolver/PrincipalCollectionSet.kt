package de.codecentric.janus.carddav.resolver

import org.w3c.dom.Element

class PrincipalCollectionSet(var href: String) : Prop(NAME, NAMESPACE) {

    companion object {
        const val NAME = "principal-collection-set"
        const val NAMESPACE = "DAV:"
    }

    override fun resolve(parent: Element): Element {
        return HrefContainerBuilder.build(parent, NAME, NAMESPACE, href)
    }
}