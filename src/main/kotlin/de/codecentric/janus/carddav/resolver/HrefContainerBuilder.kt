package de.codecentric.janus.carddav.resolver

import org.w3c.dom.Element

class HrefContainerBuilder {

    companion object {
        fun build(parent: Element, name: String, namespace: String, href: String): Element {
            val doc = parent.ownerDocument
            val element = parent.ownerDocument.createElementNS(namespace, name)
            parent.appendChild(element)

            val hrefElement = doc.createElement("href")
            hrefElement.appendChild(doc.createTextNode(href))
            element.appendChild(hrefElement)

            return element
        }
    }
}