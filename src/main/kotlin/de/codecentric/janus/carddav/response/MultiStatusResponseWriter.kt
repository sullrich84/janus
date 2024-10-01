package de.codecentric.janus.carddav.response

import de.codecentric.janus.Namespace
import de.codecentric.janus.Namespace.DAV
import de.codecentric.janus.Status.NOT_FOUND
import de.codecentric.janus.Status.OK
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.nio.charset.StandardCharsets.UTF_8

/**
 * Builder for creating a multi status response defined by the CardDAV protocol.
 *
 * @author Sebastian Ullrich
 * @since 1.0.0
 */
class MultiStatusResponseWriter(source: MultiStatusResponse) {

    @Suppress("JoinDeclarationAndAssignment")
    private var document: Node

    /**
     * Initialize the builder by creating the expected multistatus response structure.
     */
    init {
        document = xml("multistatus") {
            encoding = "utf-8"
            xmlns = DAV.uri

            // Append all namespaces referenced in the response
            // TODO: Optimize usage of namespaces
            Namespace.entries
                .filterNot { it.isDefault }
                .forEach { namespace(it.abbreviation, it.uri) }

            for (response in source.responses) {
                "response" {
                    "href" {
                        text(response.href)
                    }

                    if (response.ok.isNotEmpty()) {
                        "propstat" {
                            "prop" {
                                response.ok.forEach { prop ->
                                    addElement(prop)
                                }
                            }
                            "status" {
                                unsafeText(OK.status)
                            }
                        }
                    }

                    if (response.notFound.isNotEmpty()) {
                        "propstat" {
                            "prop" {
                                response.notFound.forEach { prop ->
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

            // Append sync token and sync level if available
            if (source.syncToken != null) {
                "sync-token" {
                    text(source.syncToken)
                }
            }
        }
    }

    /**
     * Build the multistatus response output stream
     */
    fun write(): ByteArrayOutputStream {
        val outputStream = ByteArrayOutputStream()
        document.toString(false).byteInputStream(UTF_8).copyTo(outputStream)
        return outputStream
    }
}