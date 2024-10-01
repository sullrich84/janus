package de.codecentric.apus.carddav.resolver.command

import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.request.RequestMethod.SYNC_COLLECTION
import de.codecentric.apus.carddav.response.MultiStatusResponse
import de.codecentric.apus.vcard.VCardService
import kotlinx.datetime.LocalDateTime
import org.springframework.stereotype.Component

@Component
class SyncCollectionResolver(private val service: VCardService) : CommandResolver(SYNC_COLLECTION) {

    override fun resolveLocations(context: RequestContext): Set<String> {
        val lastSyncToken = context.command?.syncToken
        check(lastSyncToken != null) { "Missing sync-token" }

        // Parse the last sync token to local date time
        val lastSync = LocalDateTime.Formats.ISO.parse(lastSyncToken)
        val updatedSince = service.getUpdatedSince(lastSync)

        return updatedSince.map { "/${context.principal}/addressbook/${it.uid}.vcf" }.toSet()
    }

    override fun updateResponse(context: RequestContext, response: MultiStatusResponse): MultiStatusResponse {
        val syncToken = service.getLatestSyncToken()
        return response.copy(syncToken = syncToken)
    }
}