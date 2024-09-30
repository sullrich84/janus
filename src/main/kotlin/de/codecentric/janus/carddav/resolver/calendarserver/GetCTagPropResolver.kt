package de.codecentric.janus.carddav.resolver.calendarserver

import de.codecentric.janus.carddav.request.CardDavRequestContext
import de.codecentric.janus.carddav.resolver.ResolverContext
import de.codecentric.janus.vcard.VCardService
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

/**
 * Resolver for the CTag (Collection Tag) property in CalDAV/CardDAV contexts.
 *
 * The CTag is a unique identifier that changes whenever any resource within a collection
 * (e.g., a calendar or address book) is modified. It allows clients to quickly determine
 * if any changes have occurred in the collection without examining each individual item.
 *
 * @property service The VCardService used to retrieve the CTag value.
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
@Component
class GetCTagPropResolver(private val service: VCardService) : CalendarServerPropResolver("getctag") {

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            text(service.getLatestCTag())
        }
    }
}