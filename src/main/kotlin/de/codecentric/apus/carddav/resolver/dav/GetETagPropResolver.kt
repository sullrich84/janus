package de.codecentric.apus.carddav.resolver.dav

import de.codecentric.apus.carddav.request.CardDavRequestContext
import de.codecentric.apus.carddav.resolver.Helper.getUidFromHref
import de.codecentric.apus.carddav.resolver.ResolverContext
import de.codecentric.apus.vcard.VCardService
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

/**
 * Resolver for the ETag (Entity Tag) property in WebDAV contexts.
 *
 * The ETag is a unique identifier for a specific version of a resource. It allows
 * clients to make conditional requests and helps in caching and concurrency control.
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
@Component
class GetETagPropResolver(private val service: VCardService) : DavPropResolver("getetag") {

    override fun supports(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Boolean {
        return super.supports(
            resolverContext,
            cardDavRequestContext
        ) && (isVCardRequest(resolverContext) || isAddressbookRequest(resolverContext))
    }

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            val eTag = if (isVCardRequest(resolverContext)) {
                val uid = resolverContext.href.substringAfterLast("/").substringBeforeLast(".vcf")
                service.getETag(uid)
            } else {
                service.getLatestSyncToken()
            }

            text(eTag)
        }
    }

    private fun isVCardRequest(resolverContext: ResolverContext): Boolean {
        return resolverContext.href.endsWith(".vcf") && service.has(getUidFromHref(resolverContext.href))
    }

    private fun isAddressbookRequest(resolverContext: ResolverContext): Boolean {
        return resolverContext.href.endsWith("/addressbook/")
    }
}