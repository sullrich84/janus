package de.codecentric.apus.vcard

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository
import org.springframework.stereotype.Repository
import java.io.File
import java.io.FileNotFoundException

@Repository
@OptIn(ExperimentalSerializationApi::class)
class VCardRepository(private val configuration: VCardConfiguration) : InMemoryAuditEventRepository() {

    private val json = Json { prettyPrint = true }

    fun find(uid: String): VCard {
        val file = File("${configuration.path}/$uid.json")
        if (file.exists().not()) throw FileNotFoundException("VCard with uid $uid not found")

        return json.decodeFromStream(file.inputStream())
    }

    fun findAllUid(): Set<String> {
        val directory = File(configuration.path)
        if (directory.exists().not()) return emptySet()

        val vCards = directory.listFiles() ?: emptyArray()
        return vCards
            .filter { it.extension == "json" }
            .map { it.nameWithoutExtension }
            .toSet()
    }

    fun findAll(): Set<VCard> {
        return findAllUid()
            .map { find(it) }
            .toSet()
    }

    fun save(vCard: VCard) {
        val file = File("${configuration.path}/${vCard.uid}.json")
        file.parentFile.mkdirs()

        json.encodeToStream(vCard, file.outputStream())
    }

    fun exists(uid: String): Boolean {
        val file = File("${configuration.path}/$uid.json")
        return file.exists()
    }
}