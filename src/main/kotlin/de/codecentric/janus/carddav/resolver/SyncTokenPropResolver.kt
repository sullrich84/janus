package de.codecentric.janus.carddav.resolver

import de.codecentric.janus.carddav.vcard.VCardService
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class SyncTokenPropResolver(private val service: VCardService) : PropResolver("sync-token") {

    override fun supports(resolverContext: ResolverContext): Boolean {
        return super.supports(resolverContext)
                && resolverContext.href == "/${resolverContext.principal}/addressbook/"
    }

    override fun resolve(context: ResolverContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            text(service.getLatestSyncToken())
        }
    }
}