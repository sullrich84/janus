package de.codecentric.apus.vcard

import kotlinx.datetime.LocalDateTime
import org.springframework.stereotype.Service

@Service
class VCardService(private val repository: VCardRepository) {

    fun getLatestSyncToken(): String {
        return getAll().maxOfOrNull { it.updatedAt }?.toString() ?: createNewToken()
    }

    fun getLatestCTag(): String {
        return getAll().maxOfOrNull { it.updatedAt }?.toString() ?: createNewToken()
    }

    fun getAll(): Set<VCard> {
        return repository.findAll()
    }

    fun getETag(uid: String): String {
        return repository.find(uid).updatedAt.toString()
    }

    fun get(uid: String): VCard {
        return repository.find(uid)
    }

    fun has(uid: String): Boolean {
        return repository.exists(uid)
    }

    fun getUpdatedSince(lastSync: LocalDateTime): Set<VCard> {
        return repository.findAll().filter { it.updatedAt > lastSync }.toSet()
    }

    private fun createNewToken(): String {
        val timestamp = LocalDateTime(2024, 1, 1, 0, 0, 0, 0)
        return LocalDateTime.Formats.ISO.format(timestamp)
    }
}