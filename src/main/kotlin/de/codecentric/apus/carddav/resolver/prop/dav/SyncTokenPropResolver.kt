package de.codecentric.apus.carddav.resolver.prop.dav

import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.resolver.prop.ResolverContext
import de.codecentric.apus.vcard.VCardService
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class SyncTokenPropResolver(private val service: VCardService) : DavPropResolver("sync-token") {

    override fun supports(resolverContext: ResolverContext, requestContext: RequestContext): Boolean {
        return super.supports(resolverContext, requestContext)
                && resolverContext.href == "/${requestContext.principal}/addressbook/"
    }

    override fun resolve(resolverContext: ResolverContext, requestContext: RequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            text(service.getLatestSyncToken())
        }
    }
}