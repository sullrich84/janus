package de.codecentric.janus.carddav.request

/**
 * Internally used representation of a propfind request defined by the CardDAV protocol.
 *
 * @param props The requested properties by the CardDAV client
 * @param nsAliases The namespace aliases defined by the properties
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
data class PropFindRequest(
    val props: Map<String, String>,
    val nsAliases: Map<String, String>
)

