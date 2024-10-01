package de.codecentric.janus.carddav.response

import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.stereotype.Component

/**
 * Custom HTTP message converter for converting a [MultiStatusResponse] to XML.
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
@Component
class MultiStatusResponseConverter : HttpMessageConverter<MultiStatusResponse> {

    companion object {
        private val TEXT_XML_UTF8 = MediaType.valueOf("text/xml; charset=utf-8")
    }

    override fun canRead(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return false
    }

    override fun canWrite(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return MultiStatusResponse::class.java.isAssignableFrom(clazz) && TEXT_XML_UTF8.includes(mediaType)
    }

    override fun getSupportedMediaTypes(): MutableList<MediaType> {
        return mutableListOf(TEXT_XML_UTF8)
    }

    override fun write(response: MultiStatusResponse, contentType: MediaType?, outputMessage: HttpOutputMessage) {
        return MultiStatusResponseWriter(response).write().writeTo(outputMessage.body)
    }

    override fun read(clazz: Class<out MultiStatusResponse>, inputMessage: HttpInputMessage): MultiStatusResponse {
        throw UnsupportedOperationException("MultiStatusResponseConverter does not support reading")
    }
}
