package de.codecentric.janus.carddav.prop

import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.format.DateTimeFormatter

@Component
class SyncTokenPropResolver : PropResolver("sync-token") {

    override fun resolve(): Node {
        return xml(namespace.appendPrefix(propName)) {
            text(DateTimeFormatter.ISO_INSTANT.format(Instant.now()))
        }
    }
}