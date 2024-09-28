package de.codecentric.janus.carddav.prop

import de.codecentric.janus.Namespace
import de.codecentric.janus.carddav.vcard.VCardService
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.format.DateTimeFormatter

@Component
class SyncTokenPropResolver(private val service: VCardService) : PropResolver("sync-token") {

    override fun supports(propName: String, namespace: Namespace, context: ResolverContext): Boolean {
        return super.supports(propName, namespace, context)
                && context.href == "/${context.principal}/addressbook/"
    }

    override fun resolve(context: ResolverContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            text(service.getLatestSyncToken())
        }
    }
}