package de.codecentric.janus.carddav.response

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "response")
data class Response(
    @JacksonXmlProperty(localName = "href")
    val href: String,

    @JacksonXmlProperty(localName = "propstat")
    @JacksonXmlElementWrapper(useWrapping = false)
    val propStatus: List<PropStatus>
)
