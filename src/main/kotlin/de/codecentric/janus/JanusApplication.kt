package de.codecentric.janus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JanusApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<JanusApplication>(*args)
        }
    }
}
