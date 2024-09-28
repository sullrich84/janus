package de.codecentric.janus.carddav.resolver

import de.codecentric.janus.Namespace.CALENDAR_SERVER
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class GetCTagPropResolver : PropResolver("getctag", CALENDAR_SERVER) {

    override fun resolve(context: ResolverContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "href" {
                text("/codecentric/")
            }
        }
    }
}