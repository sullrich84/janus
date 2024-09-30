package de.codecentric.janus.carddav.resolver.dav

import de.codecentric.janus.carddav.request.CardDavRequestContext
import de.codecentric.janus.carddav.resolver.ResolverContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class CurrentUserPrivilegeSetPropResolver : DavPropResolver("current-user-privilege-set") {

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            listOf("read", "all", "write", "write-properties", "write-content").forEach {
                "privilege" {
                    it {}
                }
            }
        }
    }
}