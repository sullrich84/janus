package de.codecentric.janus.carddav.resolver.carddav

import de.codecentric.janus.carddav.request.CardDavRequestContext
import de.codecentric.janus.carddav.resolver.Helper.getUidFromHref
import de.codecentric.janus.carddav.resolver.ResolverContext
import de.codecentric.janus.vcard.VCardService
import de.codecentric.janus.vcard.VCardWriter
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class AddressDataPropResolver(private val service: VCardService, private val writer: VCardWriter) :
    CardDavPropResolver("address-data") {

    override fun supports(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Boolean {
        return super.supports(resolverContext, cardDavRequestContext)
                && resolverContext.href.endsWith(".vcf")
                && service.hasVCard(getUidFromHref(resolverContext.href))
    }

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            val vcard = service.getVCard(getUidFromHref(resolverContext.href))
            writer.write(vcard).forEach { text(it) }
        }
    }
}