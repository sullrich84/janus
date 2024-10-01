package de.codecentric.apus.carddav.request

import java.net.URI

data class RequestContext(
    val method: RequestMethod,
    val requestUri: URI,
    val principal: String,
    var command: WebDavRequest? = null,
    val depth: Int = 0,
    val brief: Boolean = true,
    val agent: String = "n/a"
)
