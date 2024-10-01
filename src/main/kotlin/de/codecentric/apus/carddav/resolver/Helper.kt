package de.codecentric.apus.carddav.resolver

object Helper {
    fun getUidFromHref(href: String): String {
        return href.substringAfterLast("/").substringBeforeLast(".vcf")
    }
}