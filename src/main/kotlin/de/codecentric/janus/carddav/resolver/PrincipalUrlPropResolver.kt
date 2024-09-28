package de.codecentric.janus.carddav.resolver

import de.codecentric.janus.carddav.request.CardDavRequestContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class PrincipalUrlPropResolver : PropResolver("principal-URL") {

    override fun supports(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Boolean {
        return super.supports(resolverContext, cardDavRequestContext)
                && resolverContext.href != "/"
                && resolverContext.href != "/${cardDavRequestContext.principal}/addressbook/"
                && cardDavRequestContext.principal != null
    }

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "href" {
                text("/${cardDavRequestContext.principal}/")
            }
        }
    }
}