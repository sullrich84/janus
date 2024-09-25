package de.codecentric.janus.carddav.response

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.dataformat.xml.XmlMapper


class PropStatusSerializer(t: Class<PropStatus>?) : StdSerializer<PropStatus>(t) {

    constructor() : this(null)

    private val mapper = XmlMapper()

    override fun serialize(propStatus: PropStatus, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeStartObject()

        gen.writeRaw("<prop>")
        for (prop in propStatus.props){
            gen.writeRaw(mapper.writeValueAsString(prop))
        }
        gen.writeRaw("</prop>")

        gen.writeStringField("status", propStatus.status)
        gen.writeEndObject()
    }
}
