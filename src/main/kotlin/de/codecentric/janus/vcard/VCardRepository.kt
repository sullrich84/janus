package de.codecentric.janus.vcard

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

    private val json = Json {
        prettyPrint = true
    }

    fun find(uid: String): VCard {
        val file = File("${configuration.path}/$uid.json")
        if (file.exists().not()) throw FileNotFoundException("VCard with uid $uid not found")

        return json.decodeFromStream(file.inputStream())
    }

    fun save(vCard: VCard) {
        val file = File("${configuration.path}/${vCard.uid}.json")
        file.parentFile.mkdirs()

        json.encodeToStream(vCard, file.outputStream())
    }
}