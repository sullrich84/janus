package de.codecentric.janus.carddav.resolver

import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class PrincipalUrlPropResolver : PropResolver("principal-URL") {

    override fun supports(resolverContext: ResolverContext): Boolean {
        return super.supports(resolverContext)
                && resolverContext.href != "/"
                && resolverContext.principal != null
    }

    override fun resolve(context: ResolverContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "href" {
                text("/${context.principal}/")
            }
        }
    }
}