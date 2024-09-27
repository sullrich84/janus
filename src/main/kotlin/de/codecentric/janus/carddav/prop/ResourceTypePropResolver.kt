package de.codecentric.janus.carddav.prop

import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class ResourceTypePropResolver : PropResolver("resourcetype") {

    override fun resolve(): Node {
        return xml(namespace.appendPrefix(propName)) {
            "CR:addressbook" {}
            "principal" {}
            "collection" {}
        }
    }
}
