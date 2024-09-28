package de.codecentric.janus.carddav.resolver

import de.codecentric.janus.carddav.request.CardDavRequestContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class DisplayNamePropResolver : PropResolver("displayname") {

    override fun supports(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Boolean {
        return super.supports(resolverContext, cardDavRequestContext)
                && resolverContext.principal.isNullOrEmpty().not()
                && resolverContext.href == "/${resolverContext.principal}/addressbook/"
    }

    override fun resolve(context: ResolverContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            text("codecentric")
        }
    }
}