package de.codecentric.apus.carddav.request

import java.net.URI

data class RequestContext(
    val requestUri: URI,
    var principal: String? = "anonymous",
    var command: WebDavRequest? = null,
    val locations: Set<String>? = emptySet(),
    val depth: Int = 0,
    val brief: Boolean = true,
    val agent: String = "n/a"
)
