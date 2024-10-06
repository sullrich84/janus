package de.codecentric.apus.carddav.response

import org.springframework.http.*

class MultiStatusResponseEntity : ResponseEntity<MultiStatusResponse> {

    companion object {
        private val OK_HEADERS: HttpHeaders = HttpHeaders().apply {
            set("Content-Type", "text/xml; charset=utf-8")
            set("DAV", "1, 2, 3, addressbook")
            set("Allow", "GET, OPTIONS, PROPFIND, REPORT")
        }

        private val MULTI_STATUS_HEADERS: HttpHeaders = HttpHeaders().apply {
            set("Content-Type", "text/xml; charset=utf-8")
            set("DAV", "1, 2, 3, addressbook")
        }
    }

    constructor(status: HttpStatus) : super(MultiStatusResponse(emptyList()), OK_HEADERS, status)
    constructor(body: MultiStatusResponse, status: HttpStatus) : super(body, MULTI_STATUS_HEADERS, status)
}
