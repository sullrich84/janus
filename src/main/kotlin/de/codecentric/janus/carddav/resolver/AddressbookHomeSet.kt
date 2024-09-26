package de.codecentric.janus.carddav.resolver

import org.w3c.dom.Element

class AddressbookHomeSet(var href: String) : Prop(NAME, NAMESPACE) {

    companion object {
        const val NAME = "addressbook-home-set"
        const val NAMESPACE = "urn:ietf:params:xml:ns:carddav"
    }

    override fun resolve(parent: Element): Element {
        return HrefContainerBuilder.build(parent, NAME, NAMESPACE, href)
    }
}