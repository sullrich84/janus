package de.codecentric.janus.carddav.prop

import de.codecentric.janus.Namespace
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class PrincipalUrlPropResolver : PropResolver("principal-URL") {

    override fun supports(propName: String, namespace: Namespace, context: ResolverContext): Boolean {
        return super.supports(propName, namespace, context)
                && context.href != "/"
                && context.principal != null
    }

    override fun resolve(context: ResolverContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "href" {
                text("/${context.principal}/")
            }
        }
    }
}