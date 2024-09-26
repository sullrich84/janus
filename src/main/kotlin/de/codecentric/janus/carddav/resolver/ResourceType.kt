package de.codecentric.janus.carddav.resolver

import org.w3c.dom.Element

class ResourceType : Prop(NAME, "DAV:") {

    companion object {
        const val NAME = "resourcetype"
    }

    override fun resolve(parent: Element): Element {
        val doc = parent.ownerDocument
        val element = doc.createElement("resourcetype")
        parent.appendChild(element)

        val collection = doc.createElement("collection")
        element.appendChild(collection)

        return element
    }
}