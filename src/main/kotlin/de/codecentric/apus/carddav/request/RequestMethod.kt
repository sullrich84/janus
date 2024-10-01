package de.codecentric.apus.carddav.request

enum class RequestMethod(val value: String) {
    PROPFIND("propfind"),
    SYNC_COLLECTION("sync-collection"),
    ADDRESSBOOK_MULTIGET("addressbook-multiget");

    companion object {
        fun fromString(value: String): RequestMethod {
            return entries.first { it.name == value || it.value == value }
        }
    }
}