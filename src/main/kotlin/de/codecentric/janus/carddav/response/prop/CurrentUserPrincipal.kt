package de.codecentric.janus.carddav.response.prop

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "current-user-principal")
data class CurrentUserPrincipal(val href: String) : Prop()
