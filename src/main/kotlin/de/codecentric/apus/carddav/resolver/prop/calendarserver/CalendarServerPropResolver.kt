package de.codecentric.apus.carddav.resolver.prop.calendarserver

import de.codecentric.apus.carddav.Namespace.CALENDAR_SERVER
import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.resolver.prop.PropResolver
import de.codecentric.apus.carddav.resolver.prop.ResolverContext
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
    abstract override fun resolve(resolverContext: ResolverContext, requestContext: RequestContext): Node
}