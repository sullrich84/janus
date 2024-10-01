package de.codecentric.apus.carddav.resolver.dav

import de.codecentric.apus.carddav.request.CardDavRequestContext
import de.codecentric.apus.carddav.resolver.ResolverContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class DisplayNamePropResolver : DavPropResolver("displayname") {

    override fun supports(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Boolean {
        return super.supports(resolverContext, cardDavRequestContext)
                && cardDavRequestContext.principal.isNullOrEmpty().not()
                && resolverContext.href == "/${cardDavRequestContext.principal}/addressbook/"
    }

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            text("${cardDavRequestContext.principal}'s Addressbook")
        }
    }
}