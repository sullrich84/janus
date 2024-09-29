@file:Suppress("HttpUrlsUsage")

package de.codecentric.janus

enum class Namespace(val isDefault: Boolean, val abbreviation: String, val uri: String) {

    DAV(true, "DAV", "DAV:"),
    CARDDAV(false, "CR", "urn:ietf:params:xml:ns:carddav"),
    CALENDAR_SERVER(false, "CS", "http://calendarserver.org/ns/"),
    ME(false, "ME", "http://me.com/_namespace/");

    fun appendPrefix(propName: String): String {
        if (isDefault) return propName
        return "${abbreviation}:$propName"
    }

    companion object {
        fun fromString(lookup: String): Namespace {
            return Namespace.entries.first { it.abbreviation == lookup || it.uri == lookup }
        }
    }
}