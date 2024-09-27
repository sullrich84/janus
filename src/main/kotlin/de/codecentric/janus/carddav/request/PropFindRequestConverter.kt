package de.codecentric.janus.carddav.request

import de.codecentric.janus.Namespace
import de.codecentric.janus.Namespace.DAV
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.MediaType.TEXT_XML
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.stereotype.Component
import org.w3c.dom.Element
import org.xml.sax.InputSource
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Custom HTTP message converter for converting a PropFind request from XML to a [PropFindRequest] object.
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
@Component
class PropFindRequestConverter : HttpMessageConverter<PropFindRequest> {

    override fun canRead(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return PropFindRequest::class.java.isAssignableFrom(clazz) && TEXT_XML.includes(mediaType)
    }

    override fun canWrite(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return false
    }

    override fun getSupportedMediaTypes(): MutableList<MediaType> {
        return mutableListOf(TEXT_XML)
    }

    override fun write(t: PropFindRequest, contentType: MediaType?, outputMessage: HttpOutputMessage) {
        throw UnsupportedOperationException("PropFindRequestConverter does not support writing")
    }

    override fun read(clazz: Class<out PropFindRequest>, inputMessage: HttpInputMessage): PropFindRequest {
        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val xmlDocument = documentBuilder.parse(InputSource(inputMessage.body))
        val rootElement = xmlDocument.documentElement

        check(rootElement.nodeName.endsWith("propfind")) { "Invalid root element" }

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