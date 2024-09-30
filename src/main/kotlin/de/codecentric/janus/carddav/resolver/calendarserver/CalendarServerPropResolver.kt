package de.codecentric.janus.carddav.resolver.calendarserver

import de.codecentric.janus.Namespace.*
import de.codecentric.janus.carddav.request.CardDavRequestContext
import de.codecentric.janus.carddav.resolver.PropResolver
import de.codecentric.janus.carddav.resolver.ResolverContext
import org.redundent.kotlin.xml.Node

/**
 * Resolves a property for a given request context.
 *
 * @param propName the name of the calendar server property
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
abstract class CalendarServerPropResolver(override val propName: String) : PropResolver(propName, CALENDAR_SERVER) {

    /**
     * Resolves the property for the given request context.
     */
    abstract override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node
}