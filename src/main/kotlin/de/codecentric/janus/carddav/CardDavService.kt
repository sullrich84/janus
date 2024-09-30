package de.codecentric.janus.carddav

import de.codecentric.janus.Namespace
import de.codecentric.janus.carddav.request.CardDavRequestContext
import de.codecentric.janus.carddav.request.WebDavRequest
import de.codecentric.janus.carddav.request.WebDavRequest.RequestMethod.SYNC_COLLECTION
import de.codecentric.janus.carddav.resolver.PropResolver
import de.codecentric.janus.carddav.resolver.ResolverContext
import de.codecentric.janus.carddav.response.MultiStatusResponse
import de.codecentric.janus.carddav.response.StatusResponse
import de.codecentric.janus.vcard.VCardService
import de.codecentric.janus.vcard.VCardWriter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.byUnicodePattern
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

/**
 * Service class for resolving CardDAV properties.
 *
 * @param resolvers The list of available property resolvers in ApplicationContext

 * @author Sebastian Ullrich
 * @since 1.0.0
 */
@Component
class CardDavService(private val resolvers: MutableList<out PropResolver>, private val service: VCardService) {

    fun resolve(cardDavRequestContext: CardDavRequestContext): MultiStatusResponse {
        if (cardDavRequestContext.webDavRequest.method == SYNC_COLLECTION) {
            return resolveSyncCollection(cardDavRequestContext)
        }

        val responses = cardDavRequestContext.locations.map { resolveStatusResponse(it, cardDavRequestContext) }
        return MultiStatusResponse(responses = responses.toList())
    }

    fun resolveSyncCollection(cardDavRequestContext: CardDavRequestContext): MultiStatusResponse {
        val lastSyncToken = cardDavRequestContext.webDavRequest.syncToken!!
        val lastSync = LocalDateTime.Formats.ISO.parse(lastSyncToken)
        val updatedSince = service.getUpdatedSince(lastSync)

        val updatedResourceLocations = updatedSince
            .map { "/codecentric/addressbook/${it.uid}.vcf" }
            .map { resolveStatusResponse(it, cardDavRequestContext) }
            .toList()

        val syncToken = service.getLatestSyncToken()

        return MultiStatusResponse(responses = updatedResourceLocations, syncToken = syncToken)
    }

    private fun resolveStatusResponse(location: String, cardDavRequestContext: CardDavRequestContext): StatusResponse {
        val okProps = mutableListOf<Node>()
        val notFoundProps = mutableListOf<Node>()
        val namespaces = mutableSetOf<Namespace>()

        cardDavRequestContext.webDavRequest.props.forEach { prop ->
            val resolverContext = ResolverContext(prop.name, prop.namespace, location)
            val resolver = resolvers.firstOrNull { it.supports(resolverContext, cardDavRequestContext) }

            namespaces.add(prop.namespace)

            if (resolver != null) {
                val node = resolver.resolve(resolverContext, cardDavRequestContext)
                okProps.add(node)
            } else {
                val prefixedPropName = prop.namespace.appendPrefix(prop.name)
                val node = xml(prefixedPropName)
                notFoundProps.add(node)
            }
        }

        return StatusResponse(location, okProps, notFoundProps)
    }
}