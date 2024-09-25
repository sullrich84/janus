package de.codecentric.janus.carddav.response.prop

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "resourcetype")
data class ResourceType(val collection: String? = null): Prop()
