package de.codecentric.janus.carddav.resolver

import org.w3c.dom.Element

class PrincipalUrl(var href: String) : Prop(NAME, NAMESPACE) {

    companion object {
        const val NAME = "principal-URL"
        const val NAMESPACE = "DAV:"
    }

    override fun resolve(parent: Element): Element {
        return HrefContainerBuilder.build(parent, NAME, NAMESPACE, href)
    }
}