package de.codecentric.janus.carddav.prop

import de.codecentric.janus.carddav.request.CardDavContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class PrincipalUrlPropResolver : PropResolver("principal-URL") {

    override fun supports(resolverContext: ResolverContext, cardDavContext: CardDavContext): Boolean {
        return super.supports(resolverContext, cardDavContext)
                && resolverContext.href != "/"
                && resolverContext.principal != null
    }

    override fun resolve(context: ResolverContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "href" {
                text("/${context.principal}/")
            }
        }
    }
}