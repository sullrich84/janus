package de.codecentric.janus.carddav.resolver

import de.codecentric.janus.Namespace

data class ResolverContext(
    val propName: String,
    val namespace: Namespace,
    val href: String,
    val principal: String?,
)
