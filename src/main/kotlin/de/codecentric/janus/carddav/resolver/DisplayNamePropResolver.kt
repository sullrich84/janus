package de.codecentric.janus.carddav.resolver

import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class DisplayNamePropResolver : PropResolver("displayname") {

    override fun supports(resolverContext: ResolverContext): Boolean {
        return super.supports(resolverContext)
                && resolverContext.principal.isNullOrEmpty().not()
                && resolverContext.href == "/${resolverContext.principal}/addressbook/"
    }

    override fun resolve(context: ResolverContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            text("codecentric")
        }
    }
}