package de.codecentric.janus.carddav.resolver

/**
 * Worker chain interface for resolving properties.
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
interface PropResolver<T: Prop> {

    /**
     * Check if the resolver supports the given property name
     */
    fun supports(propName: String): Boolean

    /**
     * Resolve the property
     */
    fun resolve(): T
}