package de.codecentric.janus.carddav.request

import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.MediaType.TEXT_XML
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.stereotype.Component
import org.w3c.dom.Element
import org.xml.sax.InputSource
import javax.xml.parsers.DocumentBuilderFactory


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

        var nsAliases = getNameSpaceAliases(rootElement)
        val requestedProps = mutableMapOf<String, String>()

        val propElements = rootElement.firstChild.nextSibling.childNodes

        for (i in 0 until propElements.length) {
            val node = propElements.item(i)
            if (node !is Element) continue

            val additionalNsAliases = getNameSpaceAliases(node)
            nsAliases = nsAliases.plus(additionalNsAliases)

            val (nsAlias, name) = node.nodeName.split(":")
            requestedProps[name] = nsAlias
        }

        return PropFindRequest(prop = requestedProps, nsAliases = nsAliases)
    }

    private fun getNameSpaceAliases(element: Element): Map<String, String> {
        val nameSpaceAliases = mutableMapOf<String, String>()
        for (i in 0 until element.attributes.length) {
            val attribute = element.attributes.item(i)
            val (_, nsAlias) = attribute.nodeName.split(":")
            nameSpaceAliases[nsAlias] = attribute.nodeValue
        }

        return nameSpaceAliases
    }
}