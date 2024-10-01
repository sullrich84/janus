package de.codecentric.janus.carddav.resolver.dav

import de.codecentric.janus.Namespace.CARDDAV
import de.codecentric.janus.carddav.request.CardDavRequestContext
import de.codecentric.janus.carddav.resolver.ResolverContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class ResourceTypePropResolver : DavPropResolver("resourcetype") {

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            when (resolverContext.href) {
                "/${cardDavRequestContext.principal}/" -> {
                    "principal" {}
                }

                "/${cardDavRequestContext.principal}/addressbook/" -> {
                    namespace(CARDDAV.abbreviation, CARDDAV.uri)
                    (CARDDAV.appendPrefix("addressbook")) {}
                }
            }

            "collection" {}
        }
    }
}
