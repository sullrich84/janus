package de.codecentric.janus.carddav.resolver

import org.springframework.stereotype.Component

@Component
class SupportedReportSetPropResolver : PropResolver<SupportedReportSet> {

    override fun supports(propName: String): Boolean {
        return propName == SupportedReportSet.NAME
    }

    override fun resolve(): SupportedReportSet {
        return SupportedReportSet()
    }
}