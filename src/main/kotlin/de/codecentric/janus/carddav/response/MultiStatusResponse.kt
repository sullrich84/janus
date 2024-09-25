package de.codecentric.janus.carddav.response

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "multistatus")
data class MultiStatusResponse(
    @JacksonXmlProperty(localName = "response")
    val response: Response,

    @JacksonXmlProperty(isAttribute = true, localName = "xmlns")
    val namespace: String = "DAV:"
)