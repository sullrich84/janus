package de.codecentric.apus.carddav.resolver.prop.carddav

import de.codecentric.apus.carddav.Helper.getUidFromHref
import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.resolver.prop.ResolverContext
import de.codecentric.apus.vcard.VCardService
import de.codecentric.apus.vcard.VCardWriter
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

/**
 * Resolver for the address-data property in CardDAV contexts.
 *
 * The address-data property in CardDAV requests is used to specify
 * or retrieve the actual vCard data for address book entries.
 *
 * @param service the vcard service for retrieving the vcards
 * @param writer the vcard writer for serializing the vcards
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
@Component
class AddressDataPropResolver(private val service: VCardService, private val writer: VCardWriter) :
    CardDavPropResolver("address-data") {

    override fun supports(resolverContext: ResolverContext, requestContext: RequestContext): Boolean {
        return super.supports(resolverContext, requestContext)
                && resolverContext.href.endsWith(".vcf")
                && service.has(getUidFromHref(resolverContext.href))
    }

    override fun resolve(resolverContext: ResolverContext, requestContext: RequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            val vcard = service.get(getUidFromHref(resolverContext.href))
            writer.write(vcard).forEach { text(it + "\n") }
        }
    }
}
