package de.codecentric.janus.carddav.prop

import de.codecentric.janus.Namespace
import de.codecentric.janus.carddav.request.CardDavContext
import de.codecentric.janus.carddav.vcard.VCardService
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class SyncTokenPropResolver(private val service: VCardService) : PropResolver("sync-token") {

    override fun supports(resolverContext: ResolverContext, cardDavContext: CardDavContext): Boolean {
        return super.supports(resolverContext, cardDavContext)
                && resolverContext.href == "/${resolverContext.principal}/addressbook/"
    }

    override fun resolve(context: ResolverContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            text(service.getLatestSyncToken())
        }
    }
}