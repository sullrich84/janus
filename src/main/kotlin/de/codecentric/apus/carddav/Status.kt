package de.codecentric.apus.carddav

enum class Status(val status: String) {
    OK("HTTP/1.1 200 OK"),
    NOT_FOUND("HTTP/1.1 404 Not Found"),
}