package de.codecentric.janus.carddav.resolver

import de.codecentric.janus.Namespace
import de.codecentric.janus.Namespace.CARDDAV
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class SupportedReportSetPropResolver : PropResolver("supported-report-set") {

    override fun resolve(context: ResolverContext): Node {
        val reports = mutableListOf("expand-property", "principal-search-property-set", "principal-property-search")

        if (context.href == "/${context.principal}/addressbook/") {
            reports.add("sync-collection")
            reports.add(CARDDAV.appendPrefix("addressbook-multiget"))
            reports.add(CARDDAV.appendPrefix("addressbook-query"))
        }

        return xml(namespace.appendPrefix(propName)) {
            reports.forEach { report ->
                "supported-report" { "report" { report {} } }
            }
        }
    }
}