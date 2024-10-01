package de.codecentric.apus.carddav.request

import de.codecentric.apus.carddav.Namespace
import de.codecentric.apus.carddav.Namespace.DAV
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
class WebDavRequestReader(private val source: InputStream) {

    private val builder = DocumentBuilderFactory.newInstance()
        .apply { isNamespaceAware = true }
        .newDocumentBuilder()

    /**
     * Reads the given input stream and extracts the requested properties.
     */
    fun read(): WebDavRequest {
        val document = builder.parse(InputSource(source))
        val rootElement = document.documentElement
        val method = RequestMethod.fromString(rootElement.localName)
        val namespace = Namespace.fromString(rootElement.namespaceURI)

        val props = getProps(rootElement)
        val hrefs = getHrefs(rootElement)
        val syncToken = rootElement.getElementsByTagNameNS(DAV.uri, "sync-token").item(0)?.textContent
        val syncLevel = rootElement.getElementsByTagNameNS(DAV.uri, "sync-level").item(0)?.textContent?.toInt()

        return WebDavRequest(
            method = method,
            namespace = namespace,
            props = props,
            hrefs = hrefs,
            syncToken = syncToken,
            syncLevel = syncLevel
        )
    }

    /**
     * Extracts all <prop> from the given root element.
     */
    private fun getProps(rootElement: Element): List<WebDavRequest.Prop> {
        val props = mutableListOf<WebDavRequest.Prop>()
        val propElements = rootElement .getElementsByTagNameNS(DAV.uri, "prop")
            .item(0).childNodes

        for (i in 0 until propElements.length) {
            val node = propElements.item(i)
            if (node !is Element) continue

            val namespace = Namespace.fromString(node.namespaceURI)
            val prop = WebDavRequest.Prop(node.localName, namespace)
            props.add(prop)
        }

        return props
    }

    /**
     * Extracts all <href> from the given root element.
     */
    private fun getHrefs(rootElement: Element): List<String> {
        val hrefs = mutableListOf<String>()
        val hrefElements = rootElement.getElementsByTagNameNS(DAV.uri, "href")

        for (i in 0 until hrefElements.length) {
            val node = hrefElements.item(i)
            if (node !is Element) continue

            hrefs.add(node.textContent)
        }

        return hrefs
    }

}
