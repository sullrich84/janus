package de.codecentric.janus.vcard

import org.springframework.stereotype.Service

@Service
class VCardService(private val repository: VCardRepository) {

    fun getLatestSyncToken(): String {
        // TODO: Implement this method
        return "SYNC_TOKEN_NOT_IMPLEMENTED"
    }

    fun getLatestCTag(): String {
        // TODO: Implement this method
        return "CTAG_NOT_IMPLEMENTED"
    }

    fun getETag(uid: String): String {
        return repository.find(uid).revision.toString()
    }

    fun getVCard(uid: String): VCard {
        return repository.find(uid)
    }

    fun hasVCard(uid: String): Boolean {
        return repository.exists(uid)
    }
}