package de.codecentric.janus.carddav.request

data class PropFindRequest(val prop: Map<String, String>, val nsAliases: Map<String, String>)

