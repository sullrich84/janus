package de.codecentric.apus.carddav.resolver.carddav

import de.codecentric.apus.carddav.request.CardDavRequestContext
import de.codecentric.apus.carddav.resolver.Helper.getUidFromHref
import de.codecentric.apus.carddav.resolver.ResolverContext
import de.codecentric.apus.vcard.VCardService
import de.codecentric.apus.vcard.VCardWriter
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class AddressDataPropResolver(private val service: VCardService, private val writer: VCardWriter) :
    CardDavPropResolver("address-data") {

    override fun supports(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Boolean {
        return super.supports(resolverContext, cardDavRequestContext)
                && resolverContext.href.endsWith(".vcf")
                && service.has(getUidFromHref(resolverContext.href))
    }

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            val vcard = service.get(getUidFromHref(resolverContext.href))
            writer.write(vcard).forEach {
                text(it + "\n")
            }
        }
    }
}