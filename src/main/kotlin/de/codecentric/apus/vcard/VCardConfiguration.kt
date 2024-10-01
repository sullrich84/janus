package de.codecentric.apus.vcard

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "vcard")
data class VCardConfiguration(
    val path: String = "/var/apus/vcards",
)