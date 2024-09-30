package de.codecentric.janus

import de.codecentric.janus.vcard.VCardConfiguration
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(VCardConfiguration::class)
class JanusApplicationTests {

    @Test
    fun contextLoads() {
    }
}
