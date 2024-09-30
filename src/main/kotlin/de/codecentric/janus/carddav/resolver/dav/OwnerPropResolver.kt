package de.codecentric.janus.carddav.resolver.dav

import de.codecentric.janus.carddav.request.CardDavRequestContext
import de.codecentric.janus.carddav.resolver.ResolverContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class OwnerPropResolver : DavPropResolver("owner") {

    override fun supports(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Boolean {
        return super.supports(resolverContext, cardDavRequestContext)
                && cardDavRequestContext.principal.isNullOrEmpty().not()
                && resolverContext.href != "/${cardDavRequestContext.principal}/addressbook"
    }

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "href" {
                text("/${cardDavRequestContext.principal}/")
            }
        }
    }
}