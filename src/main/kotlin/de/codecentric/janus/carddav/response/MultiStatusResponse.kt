package de.codecentric.janus.carddav.response


/**
 * Represents a multi status response defined by the CardDAV protocol.
 *
 * @param responses The list of status responses
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
data class MultiStatusResponse(
    val responses: List<StatusResponse>,
    val syncToken: String? = null,
)