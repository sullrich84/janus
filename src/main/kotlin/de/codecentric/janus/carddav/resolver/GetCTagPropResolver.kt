package de.codecentric.janus.carddav.resolver

import de.codecentric.janus.Namespace.CALENDAR_SERVER
import de.codecentric.janus.carddav.request.CardDavRequestContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class GetCTagPropResolver : PropResolver("getctag", CALENDAR_SERVER) {

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "href" {
                text("/codecentric/")
            }
        }
    }
}