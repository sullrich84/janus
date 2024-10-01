package de.codecentric.apus.carddav

object Helper {
    fun getUidFromHref(href: String): String {
        return href.substringAfterLast("/").substringBeforeLast(".vcf")
    }
}