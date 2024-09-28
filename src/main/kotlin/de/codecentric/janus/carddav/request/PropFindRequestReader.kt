package de.codecentric.janus.carddav.request

import de.codecentric.janus.Namespace
import de.codecentric.janus.Namespace.DAV
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Reads a propfind request from the given input stream.
 *
 * @param source The input stream to read from
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
class PropFindRequestReader(private val source: InputStream) {

    private val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

    /**
     * Reads the given input stream and extracts the requested properties.
     */
    fun read(): PropFindRequest {
        val document = builder.parse(InputSource(source))
        val rootElement = document.documentElement

        check(rootElement.nodeName.endsWith("propfind")) { "Non 'propfind' root element" }

        var namespaces = getNamespaces(rootElement)
        val requestedProps = mutableMapOf<String, Namespace>()
        val propElements = rootElement.firstChild.nextSibling.childNodes

        for (i in 0 until propElements.length) {
            val node = propElements.item(i)
            if (node !is Element) continue

            // Extract lazy defined namespace aliases
            namespaces = namespaces.plus(getNamespaces(node))

            val (namespaceAlias, name) = node.nodeName.split(":")
            requestedProps[name] = namespaces[namespaceAlias] ?: DAV
        }

        return PropFindRequest(props = requestedProps, namespaces = namespaces)
    }

    /**
     * Extracts lazy defined namespace aliases from the given element.
     */
    private fun getNamespaces(element: Element): Map<String, Namespace> {
        val namespaces = mutableMapOf<String, Namespace>()

        for (i in 0 until element.attributes.length) {
            val attribute = element.attributes.item(i)
            if (attribute.nodeName.startsWith("xmlns:")) {
                val (_, namespaceAlias) = attribute.nodeName.split(":")
                namespaces[namespaceAlias] = Namespace.lookup(attribute.nodeValue)
            }
        }

        return namespaces
    }
}
