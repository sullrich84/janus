package de.codecentric.janus.carddav.resolver

interface PropResolver {

    fun supports(propName: String): Boolean
}