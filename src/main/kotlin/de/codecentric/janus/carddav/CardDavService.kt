package de.codecentric.janus.carddav

import de.codecentric.janus.Namespace
import de.codecentric.janus.carddav.request.CardDavRequestContext
import de.codecentric.janus.carddav.request.WebDavRequest
import de.codecentric.janus.carddav.request.WebDavRequest.RequestMethod.*
import de.codecentric.janus.carddav.resolver.PropResolver
import de.codecentric.janus.carddav.resolver.ResolverContext
import de.codecentric.janus.carddav.response.MultiStatusResponse
import de.codecentric.janus.carddav.response.StatusResponse
import de.codecentric.janus.vcard.VCardService
import kotlinx.datetime.LocalDateTime
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

    fun resolve(context: CardDavRequestContext): MultiStatusResponse {
        if (context.webDavRequest.method == SYNC_COLLECTION) {
            return resolveSyncCollection(context)
        }

        val enrichedContext = fillUpLocations(context)
        val responses = enrichedContext.locations.map { resolveStatusResponse(it, enrichedContext) }

        return MultiStatusResponse(responses = responses.toList())
    }

    fun fillUpLocations(context: CardDavRequestContext): CardDavRequestContext {
        val additionalLocations = when (val root = context.locations.first()) {
            "/${context.principal}/" -> {
                if (context.depth == 0) {
                    // Only the principal
                    listOf(root)
                } else {
                    // Add principal specific collections
                    listOf(root, "/${context.principal}/addressbook/")
                }
            }

            "/${context.principal}/addressbook/" -> {
                // Add collection specific resources
                when (context.webDavRequest.method) {
                    PROPFIND -> {
                        if (context.depth == 0) {
                            // Only the principal
                            listOf(root)
                        } else {
                            // All vcards in the addressbook
                            val vcardLocations = service.getAll()
                                .map { "/${context.principal}/addressbook/${it.uid}.vcf" }
                                .toList()

                            listOf(root).plus(vcardLocations)
                        }
                    }

                    ADDRESSBOOK_MULTIGET -> {
                        // All vcards that have been requested within the request
                        context.webDavRequest.hrefs.toList()
                    }

                    SYNC_COLLECTION -> {
                        // Only the vcards that have changed since the last sync
                        listOf(root)
                    }
                }
            }

            else -> listOf(root)
        }

        return context.copy(locations = additionalLocations)
    }

    fun resolveSyncCollection(context: CardDavRequestContext): MultiStatusResponse {
        val lastSyncToken = context.webDavRequest.syncToken!!
        val lastSync = LocalDateTime.Formats.ISO.parse(lastSyncToken)
        val updatedSince = service.getUpdatedSince(lastSync)

        val updatedResourceLocations = updatedSince.map { "/codecentric/addressbook/${it.uid}.vcf" }
            .map { resolveStatusResponse(it, context) }.toList()

        val syncToken = service.getLatestSyncToken()

        return MultiStatusResponse(responses = updatedResourceLocations, syncToken = syncToken)
    }

    private fun resolveStatusResponse(location: String, context: CardDavRequestContext): StatusResponse {
        val okProps = mutableListOf<Node>()
        val notFoundProps = mutableListOf<Node>()
        val namespaces = mutableSetOf<Namespace>()

        context.webDavRequest.props.forEach { prop ->
            val resolverContext = ResolverContext(prop.name, prop.namespace, location)
            val resolver = resolvers.firstOrNull { it.supports(resolverContext, context) }

            namespaces.add(prop.namespace)

            if (resolver != null) {
                val node = resolver.resolve(resolverContext, context)
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