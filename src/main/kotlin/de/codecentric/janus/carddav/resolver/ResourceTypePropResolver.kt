package de.codecentric.janus.carddav.resolver

import de.codecentric.janus.Namespace.CARDDAV
import de.codecentric.janus.carddav.request.CardDavRequestContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class ResourceTypePropResolver : PropResolver("resourcetype") {

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "collection" {}

            when (resolverContext.href) {
                "/${cardDavRequestContext.principal}/" -> {
                    "principal" {}
                }

                "/${cardDavRequestContext.principal}/addressbook/" -> {
                    namespace(CARDDAV.abbreviation, CARDDAV.uri)
                    (CARDDAV.appendPrefix("addressbook")) {}
                }
            }
        }
    }
}
