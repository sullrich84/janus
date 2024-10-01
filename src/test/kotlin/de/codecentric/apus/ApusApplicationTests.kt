package de.codecentric.apus

import de.codecentric.apus.vcard.VCardConfiguration
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(VCardConfiguration::class)
class ApusApplicationTests {

    @Test
    fun contextLoads() {
    }
}
