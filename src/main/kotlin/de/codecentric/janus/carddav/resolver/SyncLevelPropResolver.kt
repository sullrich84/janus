package de.codecentric.janus.carddav.resolver

import de.codecentric.janus.carddav.request.CardDavRequestContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class SyncLevelPropResolver : PropResolver("sync-level") {

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            text("1")
        }
    }
}