package de.codecentric.janus.vcard

import kotlinx.datetime.LocalDateTime
import org.springframework.stereotype.Service

@Service
class VCardService(private val repository: VCardRepository) {

    fun getLatestSyncToken(): String {
        return getAll().maxOfOrNull { it.revision }?.toString() ?: "0"
    }

    fun getLatestCTag(): String {
        return getAll().maxOfOrNull { it.revision }?.toString() ?: "0"
    }

    fun getAll(): Set<VCard> {
        return repository.findAll()
    }

    fun getETag(uid: String): String {
        return repository.find(uid).revision.toString()
    }

    fun get(uid: String): VCard {
        return repository.find(uid)
    }

    fun has(uid: String): Boolean {
        return repository.exists(uid)
    }

    fun getUpdatedSince(lastSync: LocalDateTime): Set<VCard> {
        return repository.findAll().filter { it.revision > lastSync }.toSet()
    }
}