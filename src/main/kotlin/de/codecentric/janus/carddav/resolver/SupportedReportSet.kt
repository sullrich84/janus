package de.codecentric.janus.carddav.resolver

import org.w3c.dom.Element

class SupportedReportSet() : Prop(NAME, NAMESPACE) {

    companion object {
        const val NAME = "supported-report-set"
        const val NAMESPACE = "DAV:"
    }

    override fun resolve(parent: Element): Element {
        val element = parent.ownerDocument.createElementNS(NAMESPACE, NAME)
        parent.appendChild(element)

        element.appendChild(createReportSet(element, "expand-property"))
        element.appendChild(createReportSet(element, "principal-search-property-set"))
        element.appendChild(createReportSet(element, "principal-property-search"))

        return element
    }

    private fun createReportSet(parent: Element, value: String): Element {
        val doc = parent.ownerDocument

        val supportedReport = doc.createElement("supported-report")
        val report = doc.createElement("report")
        val action = doc.createElement(value)

        supportedReport.appendChild(report)
        report.appendChild(action)

        return supportedReport
    }
}