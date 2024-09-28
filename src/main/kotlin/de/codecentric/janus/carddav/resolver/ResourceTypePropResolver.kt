package de.codecentric.janus.carddav.resolver

import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class ResourceTypePropResolver : PropResolver("resourcetype") {

    override fun resolve(context: ResolverContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "collection" {}

            when (context.href) {
                "/${context.principal}/" -> {
                    "principal" {}
                }

                "/${context.principal}/addressbook/" -> {
                    "CR:addressbook" {}
                }
            }
        }
    }
}
