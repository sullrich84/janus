package de.codecentric.janus.carddav.response.prop

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JacksonXmlRootElement(localName = "principal-URL")
data class PrincipalUrl(val href: String? = null) : Prop()
