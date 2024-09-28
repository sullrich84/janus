package de.codecentric.janus.carddav.resolver

import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class PrincipalCollectionSetPropResolver : PropResolver("principal-collection-set") {

    override fun resolve(context: ResolverContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "href" {
                text("/")
            }
        }
    }
}