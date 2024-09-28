package de.codecentric.janus.carddav.prop

import de.codecentric.janus.Namespace
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class OwnerPropResolver : PropResolver("owner") {

    override fun supports(propName: String, namespace: Namespace, context: ResolverContext): Boolean {
        return super.supports(propName, namespace, context)
                && context.principal.isNullOrEmpty().not()
                && context.href != "/${context.principal}/addressbook"
    }

    override fun resolve(context: ResolverContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "href" {
                text("/${context.principal}/")
            }
        }
    }
}