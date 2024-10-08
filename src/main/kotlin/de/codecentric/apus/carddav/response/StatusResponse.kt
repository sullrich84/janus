package de.codecentric.apus.carddav.response

import org.redundent.kotlin.xml.Node

/**
 * Represents a status response defined by the CardDAV protocol.
 *
 * @param href The href of the requested properties
 * @param ok The list of properties nodes that were found
 * @param notFound The list of properties nodes that were not found
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
data class StatusResponse(
    val href: String,
    val ok: List<Node>,
    val notFound: List<Node>,
)