package de.codecentric.janus.carddav.resolver.dav

import de.codecentric.janus.carddav.request.CardDavRequestContext
import de.codecentric.janus.carddav.resolver.ResolverContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

/**
 * Resolver for the ETag (Entity Tag) property in WebDAV contexts.
 *
 * The ETag is a unique identifier for a specific version of a resource. It allows
 * clients to make conditional requests and helps in caching and concurrency control.
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
@Component
class GetETagPropResolver : DavPropResolver("getetag") {

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "href" {
                text("/codecentric/")
            }
        }
    }
}