package de.codecentric.janus.carddav.request

import de.codecentric.janus.Namespace

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
    val props: List<Prop> = emptyList(),
    val hrefs: List<String> = emptyList(),
    val syncToken: String? = null,
    val syncLevel: Int? = null,
) {

    enum class RequestMethod(val value: String) {
        PROPFIND("propfind"),
        SYNC_COLLECTION("sync-collection"),
        ADDRESSBOOK_MULTIGET("addressbook-multiget");

        companion object {
            fun fromString(value: String): RequestMethod {
                return entries.first { it.value == value }
            }
        }
    }

    data class Prop(
        val name: String,
        val namespace: Namespace,
    )
}
