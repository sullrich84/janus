package de.codecentric.janus.carddav.response

import de.codecentric.janus.Namespace.DAV
import de.codecentric.janus.Status
import de.codecentric.janus.Status.NOT_FOUND
import de.codecentric.janus.Status.OK
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import java.io.ByteArrayOutputStream

/**
 * Builder for creating a multi status response defined by the CardDAV protocol.
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
class MultiStatusResponseBuilder(response: MultiStatusResponse) {

    @Suppress("JoinDeclarationAndAssignment")
    private var document: Node

    /**
     * Initialize the builder by creating the expected multistatus response structure.
     */
    init {
        document = xml("multistatus") {
            xmlns = DAV.uri

            response.ok.values.plus(response.notFound.values)
                .filterNot { it.isDefault }
                .forEach { namespace(it.abbreviation, it.uri) }

            "response" {
                "href" {
                    text(response.href)
                }

                if (response.ok.isNotEmpty()) {
                    "propstat" {
                        "prop" {
                            response.ok.forEach { (prop) ->
                                addElement(prop)
                            }
                        }
                        "status" {
                            text(OK.status)
                        }
                    }
                }

                if (response.notFound.isNotEmpty()) {
                    "propstat" {
                        "prop" {
                            response.notFound.forEach { (prop) ->
                                addElement(prop)
                            }
                        }
                        "status" {
                            text(NOT_FOUND.status)
                        }
                    }
                }
            }
        }
    }

    /**
     * Build the multistatus response output stream
     */
    fun build(): ByteArrayOutputStream {
        val outputStream = ByteArrayOutputStream()
        document.toString(true).byteInputStream().copyTo(outputStream)
        return outputStream
    }
}