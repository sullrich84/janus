package de.codecentric.janus.carddav.request

data class CardDavRequestContext(
    val depth: Int,
    val brief: Boolean,
    val locations: List<String>,
)
