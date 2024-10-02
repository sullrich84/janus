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

    fun find(filename: String): VCard {
        return File("${configuration.path}/$filename.json").run {
            if (!exists()) throw FileNotFoundException("$filename not found")
            json.decodeFromStream(inputStream())
        }
    }

    fun findAllUid(): Set<String> {
        return File(configuration.path).run {
            if (!exists()) return emptySet()

            (listFiles() ?: emptyArray())
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
        File("${configuration.path}/${vCard.persistenceName}.json").run {
            parentFile.mkdirs()
            json.encodeToStream(vCard, outputStream())
        }
    }

    fun exists(filename: String): Boolean {
        return File("${configuration.path}/$filename.json").exists()
    }
}