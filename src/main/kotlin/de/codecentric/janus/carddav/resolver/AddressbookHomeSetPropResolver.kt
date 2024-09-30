package de.codecentric.janus.carddav.resolver

import de.codecentric.janus.Namespace.CARDDAV
import de.codecentric.janus.carddav.request.CardDavRequestContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

/**
 * Resolver for the addressbook-home-set property in CardDAV contexts.
 *
 * The addressbook-home-set property identifies the URL of any WebDAV collections
 * that contain address book collections owned by the authenticated user.
 * This is a crucial property for CardDAV clients to discover available address books.
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
@Component
class AddressbookHomeSetPropResolver : PropResolver("addressbook-home-set", CARDDAV) {

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "href" {
                text("/codecentric/")
            }
        }
    }
}