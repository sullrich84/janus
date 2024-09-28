package de.codecentric.janus.carddav.resolver

import de.codecentric.janus.Namespace.CARDDAV
import de.codecentric.janus.carddav.request.CardDavRequestContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class AddressDataPropResolver : PropResolver("address-data", CARDDAV) {

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "href" {
                text("/codecentric/")
            }
        }
    }
}