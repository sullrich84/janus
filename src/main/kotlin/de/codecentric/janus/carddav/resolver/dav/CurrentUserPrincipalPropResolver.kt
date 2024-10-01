package de.codecentric.janus.carddav.resolver.dav

import de.codecentric.janus.carddav.request.CardDavRequestContext
import de.codecentric.janus.carddav.resolver.ResolverContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class CurrentUserPrincipalPropResolver : DavPropResolver("current-user-principal") {

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "href" {
                text("/${cardDavRequestContext.principal}/")
            }
        }
    }
}