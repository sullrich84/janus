package de.codecentric.apus.carddav.request

/**
 * Context of a CardDAV request.
 *
 * @param depth The depth of the request
 * @param brief Whether the response should be brief
 * @param locations The locations of the request
 * @param webDavRequest The propfind request
 * @param principal The principal of the request
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
data class CardDavRequestContext(
    val depth: Int,
    val brief: Boolean,
    val locations: List<String>,
    val webDavRequest: WebDavRequest,
    val principal: String? = "anonymous",
)
