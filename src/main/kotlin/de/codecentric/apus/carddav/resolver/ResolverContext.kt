package de.codecentric.apus.carddav.resolver

import de.codecentric.apus.carddav.Namespace

data class ResolverContext(
    val propName: String,
    val namespace: Namespace,
    val href: String,
)
