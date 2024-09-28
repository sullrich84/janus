package de.codecentric.janus.carddav.request

data class CardDavRequestContext(
    val depth: Int = 0,
    val briefPreferred: Boolean = true,
    val userAgent: String = "unknown",
)
