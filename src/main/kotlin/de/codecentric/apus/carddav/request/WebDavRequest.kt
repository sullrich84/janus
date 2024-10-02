package de.codecentric.apus.carddav.request

import de.codecentric.apus.carddav.Namespace

/**
 * Internally used representation of a propfind request defined by the CardDAV protocol.
 *
 * @param method The method of the request
 * @param namespace The namespace of the request
 * @param props The requested properties
 * @param hrefs The requested hrefs
 * @param syncToken The sync token
 * @param syncLevel The sync level
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
data class WebDavRequest(
    val method: RequestMethod,
    val namespace: Namespace,
    val props: Set<Prop> = emptySet(),
    val hrefs: Set<String> = emptySet(),
    val syncToken: String? = null,
    val syncLevel: Int? = null,
) {

    data class Prop(
        val name: String,
        val namespace: Namespace,
    )
}
