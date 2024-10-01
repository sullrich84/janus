package de.codecentric.apus.carddav.resolver.prop

import de.codecentric.apus.carddav.Namespace

data class ResolverContext(
    val propName: String,
    val namespace: Namespace,
    val href: String,
)
