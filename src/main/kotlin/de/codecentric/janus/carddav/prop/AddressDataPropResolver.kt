package de.codecentric.janus.carddav.prop

import de.codecentric.janus.Namespace.CARDDAV
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class AddressDataPropResolver : PropResolver("address-data", CARDDAV) {

    override fun resolve(): Node {
        return xml(namespace.appendPrefix(propName)) {
            "href" {
                text("/codecentric/")
            }
        }
    }
}