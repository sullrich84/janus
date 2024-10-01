package de.codecentric.apus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ApusApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<ApusApplication>(*args)
        }
    }
}
