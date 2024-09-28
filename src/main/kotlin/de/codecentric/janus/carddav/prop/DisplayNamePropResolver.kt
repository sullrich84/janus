package de.codecentric.janus.carddav.prop

import de.codecentric.janus.Namespace
import de.codecentric.janus.carddav.request.CardDavContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class DisplayNamePropResolver : PropResolver("displayname") {

    override fun supports(resolverContext: ResolverContext, cardDavContext: CardDavContext): Boolean {
        return super.supports(resolverContext, cardDavContext)
                && resolverContext.principal.isNullOrEmpty().not()
                && resolverContext.href == "/${resolverContext.principal}/addressbook/"
    }

    override fun resolve(context: ResolverContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            text("codecentric")
        }
    }
}