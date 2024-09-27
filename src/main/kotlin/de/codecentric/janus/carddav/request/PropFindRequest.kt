package de.codecentric.janus.carddav.request

import de.codecentric.janus.Namespace

/**
 * Internally used representation of a propfind request defined by the CardDAV protocol.
 *
 * @param props The requested properties by the CardDAV client
 * @param namespaces The namespaces used by the properties
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
data class PropFindRequest(
    val props: Map<String, Namespace>,
    val namespaces: Map<String, Namespace>,
)
