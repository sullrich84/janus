package de.codecentric.janus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class JanusApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<JanusApplication>(*args)
        }
    }
}
