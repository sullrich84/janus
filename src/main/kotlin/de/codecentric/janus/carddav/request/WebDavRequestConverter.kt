package de.codecentric.janus.carddav.request

import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.MediaType.TEXT_XML
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.stereotype.Component

/**
 * Custom HTTP message converter for converting a PropFind request from XML to a [WebDavRequest] object.
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
@Component
class WebDavRequestConverter : HttpMessageConverter<WebDavRequest> {

    override fun canRead(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return WebDavRequest::class.java.isAssignableFrom(clazz) && TEXT_XML.includes(mediaType)
    }

    override fun canWrite(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return false
    }

    override fun getSupportedMediaTypes(): MutableList<MediaType> {
        return mutableListOf(TEXT_XML)
    }

    override fun write(t: WebDavRequest, contentType: MediaType?, outputMessage: HttpOutputMessage) {
        throw UnsupportedOperationException("PropFindRequestConverter does not support writing")
    }

    override fun read(clazz: Class<out WebDavRequest>, inputMessage: HttpInputMessage): WebDavRequest {
       return WebDavRequestReader(inputMessage.body).read()
    }
}