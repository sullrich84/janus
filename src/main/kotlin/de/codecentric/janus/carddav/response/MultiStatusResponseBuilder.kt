package de.codecentric.janus.carddav.response

import de.codecentric.janus.carddav.resolver.Prop
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.ByteArrayOutputStream
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * Builder for creating a multi status response defined by the CardDAV protocol.
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
class MultiStatusResponseBuilder(multiStatusResponse: MultiStatusResponse) {

    private var builder: DocumentBuilder = DocumentBuilderFactory.newNSInstance().newDocumentBuilder()
    private var document: Document = builder.newDocument()

    /**
     * Initialize the builder by creating the expected multistatus response structure.
     */
    init {
        val multistatus = document.createElementNS("DAV:", "multistatus")

        // Append all used aliases
        multiStatusResponse.nsAliases.forEach { (alias, namespace) ->
            multistatus.setAttribute("xmlns:$alias", namespace)
        }

        document.appendChild(multistatus)

        val response = document.createElement("response")
        multistatus.appendChild(response)

        response.appendChild(document.createElement("href").apply {
            appendChild(document.createTextNode(multiStatusResponse.href))
        })

        createPropStat(response, multiStatusResponse.ok, "HTTP/1.1 200 OK")
        createPropStat(response, multiStatusResponse.notFound, "HTTP/1.1 404 Not Found")
    }

    /**
     * Create a propstat element with the given children and status.
     */
    private fun createPropStat(parent: Element, children: List<Prop>, status: String) {
        parent.appendChild(document.createElement("propstat").apply {
            val notFoundContainer = document.createElement("prop")
            appendChild(notFoundContainer)

            // Append all children
            children.forEach { notFoundContainer.appendChild(it.resolve(notFoundContainer)) }

            appendChild(document.createElement("status").apply {
                appendChild(document.createTextNode(status))
            })
        })
    }

    /**
     * Build the multistatus response output stream
     */
    fun build(): ByteArrayOutputStream {
        val transformer = TransformerFactory.newInstance().newTransformer()
        val source = DOMSource(document)

        val outputStream = ByteArrayOutputStream()
        val result = StreamResult(outputStream)
        transformer.transform(source, result)

        return outputStream
    }
}