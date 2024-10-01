package de.codecentric.apus.carddav.resolver.dav

import de.codecentric.apus.carddav.Namespace.CARDDAV
import de.codecentric.apus.carddav.request.CardDavRequestContext
import de.codecentric.apus.carddav.resolver.ResolverContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class SupportedReportSetPropResolver : DavPropResolver("supported-report-set") {

    override fun resolve(resolverContext: ResolverContext, cardDavRequestContext: CardDavRequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            val reports = mutableListOf("expand-property", "principal-search-property-set", "principal-property-search")
            if (resolverContext.href == "/${cardDavRequestContext.principal}/addressbook/") {
                reports.add("sync-collection")
                reports.add(CARDDAV.appendPrefix("addressbook-multiget"))
                reports.add(CARDDAV.appendPrefix("addressbook-query"))
            }

            reports.forEach { report ->
                "supported-report" {
                    "report" {
                        report {}
                    }
                }
            }
        }
    }
}