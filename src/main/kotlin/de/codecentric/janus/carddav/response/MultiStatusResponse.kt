package de.codecentric.janus.carddav.response

import de.codecentric.janus.carddav.resolver.Prop

/**
 * Represents a multi status response defined by the CardDAV protocol.
 *
 * @param href The href of the requested properties
 * @param nsAliases The namespace aliases defined by the properties
 * @param ok The list of properties that were found
 * @param notFound The list of properties that were not found
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
data class MultiStatusResponse(
    val href: String,
    val nsAliases: Map<String, String>,
    val ok: List<Prop>,
    val notFound: List<Prop>,
)