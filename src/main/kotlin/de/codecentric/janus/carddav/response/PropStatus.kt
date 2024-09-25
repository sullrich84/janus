package de.codecentric.janus.carddav.response

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import de.codecentric.janus.carddav.response.prop.Prop

@JsonSerialize(using = PropStatusSerializer::class)
data class PropStatus(val props: List<Prop>, val status: String)