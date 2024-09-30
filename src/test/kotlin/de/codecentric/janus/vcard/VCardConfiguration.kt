package de.codecentric.janus.vcard

import jakarta.annotation.PostConstruct
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Primary

@Primary
@TestConfiguration
@ConfigurationProperties(prefix = "vcard")
data class VCardConfiguration(var path: String = ""){

    @PostConstruct
    fun init() {
        // Instead of the provided path, we use the path to the resources folder
        path = this::class.java.classLoader.getResource("vcard")?.path.toString()
    }
}