package de.codecentric.janus.carddav.request

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer

class PropFindRequestDeserializer(t: Class<PropFindRequest>?): StdDeserializer<PropFindRequest>(t) {

    @Suppress("unused")
    constructor(): this(null)

    override fun deserialize(parser: JsonParser, context: DeserializationContext): PropFindRequest {
        val node = parser.codec.readTree<JsonNode>(parser)

        val requestedProps = mutableListOf<PropRequest>()
        node.get("prop").fieldNames().forEachRemaining { requestedProps.add(PropRequest(name = it)) }

        return PropFindRequest(prop = requestedProps)
    }
}