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
        val root = document.documentElement
        val method = RequestMethod.fromString(root.localName)
        val namespace = Namespace.fromString(root.namespaceURI)

        val props = getProps(root)
        val hrefs = getHrefs(root)

        val getText = { name: String -> root.getElementsByTagNameNS(DAV.uri, name).item(0)?.textContent }
        val syncToken = getText("sync-token")
        val syncLevel = getText("sync-level")?.toInt()

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
    private fun getProps(rootElement: Element): Set<WebDavRequest.Prop> {
        return rootElement.getElementsByTagNameNS(DAV.uri, "prop").item(0).childNodes.run {
            (0 until length)
                .map { item(it) }
                .filterIsInstance<Element>()
                .map { WebDavRequest.Prop(it.localName, Namespace.fromString(it.namespaceURI)) }
                .toSet()
        }
    }

    /**
     * Extracts all <href> from the given root element.
     */
    private fun getHrefs(rootElement: Element): Set<String> {
        return rootElement.getElementsByTagNameNS(DAV.uri, "href").run {
            (0 until length)
                .map { item(it) }
                .filterIsInstance<Element>()
                .map { it.textContent }
                .toSet()
        }
    }
}
