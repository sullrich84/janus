package de.codecentric.janus.carddav.prop

import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class SyncLevelPropResolver : PropResolver("sync-level") {

    override fun resolve(): Node {
        return xml(namespace.appendPrefix(propName)) {
            text("1")
        }
    }
}