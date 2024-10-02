package de.codecentric.apus.carddav.resolver.prop.dav

import de.codecentric.apus.carddav.Helper.getUidFromHref
import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.resolver.prop.ResolverContext
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

    override fun supports(resolverContext: ResolverContext, requestContext: RequestContext): Boolean {
        return super.supports(resolverContext, requestContext)
                && (isVCardRequest(resolverContext) || isAddressbookRequest(resolverContext))
    }

    override fun resolve(resolverContext: ResolverContext, requestContext: RequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            when {
                isVCardRequest(resolverContext) -> resolverContext.href
                    .substringAfterLast("/")
                    .substringBeforeLast(".vcf")
                    .let(service::getETag)

                else -> service.getLatestSyncToken()
            }.let(::text)
        }
    }

    private fun isVCardRequest(resolverContext: ResolverContext): Boolean {
        return resolverContext.href.endsWith(".vcf") && service.has(getUidFromHref(resolverContext.href))
    }

    private fun isAddressbookRequest(resolverContext: ResolverContext): Boolean {
        return resolverContext.href.endsWith("/addressbook/")
    }
}