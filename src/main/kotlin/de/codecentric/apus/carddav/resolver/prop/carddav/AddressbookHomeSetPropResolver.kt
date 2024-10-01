package de.codecentric.apus.carddav.resolver.prop.carddav

import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.resolver.prop.ResolverContext
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
class AddressbookHomeSetPropResolver : CardDavPropResolver("addressbook-home-set") {

    override fun resolve(resolverContext: ResolverContext, requestContext: RequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "href" {
                text("/${requestContext.principal}/")
            }
        }
    }
}