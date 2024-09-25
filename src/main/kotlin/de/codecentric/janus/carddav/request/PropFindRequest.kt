package de.codecentric.janus.carddav.request

import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(using = PropFindRequestDeserializer::class)
data class PropFindRequest(val prop: List<PropRequest>)

