package de.codecentric.janus.carddav.prop

import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class SupportedReportSetPropResolver : PropResolver("supported-report-set") {

    override fun resolve(): Node {
        val reports = listOf("expand-property", "principal-search-property-set", "principal-property-search")

        return xml(namespace.appendPrefix(propName)) {
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