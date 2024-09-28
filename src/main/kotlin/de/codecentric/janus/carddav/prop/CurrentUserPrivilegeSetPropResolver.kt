package de.codecentric.janus.carddav.prop

import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class CurrentUserPrivilegeSetPropResolver : PropResolver("current-user-privilege-set") {

    override fun resolve(context: ResolverContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            listOf("read", "all", "write", "write-properties", "write-content").forEach {
                "privilege" {
                    it {}
                }
            }
        }
    }
}