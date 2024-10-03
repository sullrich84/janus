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
        return File("${configuration.path}/$uid.json").run {
            if (!exists()) throw FileNotFoundException("$uid not found")
            json.decodeFromStream(inputStream())
        }
    }

    fun findAllUid(): Set<String> {
        return File(configuration.path).run {
            if (!exists()) return emptySet()

            listFiles().orEmpty()
                .filter { it.extension == "json" }
                .map { it.nameWithoutExtension }
                .toSet()
        }
    }

    fun findAll(): Set<VCard> {
        return findAllUid()
            .map { find(it) }
            .toSet()
    }

    fun save(vCard: VCard) {
        File("${configuration.path}/${vCard.uid}.json").run {
            parentFile.mkdirs()
            json.encodeToStream(vCard, outputStream())
        }
    }

    fun exists(uid: String): Boolean {
        return File("${configuration.path}/$uid.json").exists()
    }
}