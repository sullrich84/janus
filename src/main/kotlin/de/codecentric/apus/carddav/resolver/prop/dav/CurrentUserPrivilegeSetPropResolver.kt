package de.codecentric.apus.carddav.resolver.prop.dav

import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.resolver.prop.ResolverContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class CurrentUserPrivilegeSetPropResolver : DavPropResolver("current-user-privilege-set") {

    override fun resolve(resolverContext: ResolverContext, requestContext: RequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            // TODO: Reduce this to read only
            listOf("read", "all", "write", "write-properties", "write-content").forEach {
                "privilege" {
                    it {}
                }
            }
        }
    }
}