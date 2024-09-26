package de.codecentric.janus.carddav.resolver

import org.w3c.dom.Element

/**
 * Represents a property in the CardDAV namespace.
 *
 * @param name The name of the property
 * @param namespace The namespace of the property
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
open class Prop(private val name: String, private val namespace: String = "DAV:") {

    open fun resolve(parent: Element): Element {
        return parent.ownerDocument.createElementNS(namespace, name)
    }

    operator fun component1() = name
    operator fun component2() = namespace
}