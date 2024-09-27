package de.codecentric.janus.carddav

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus.MULTI_STATUS
import org.springframework.http.MediaType.TEXT_XML_VALUE
import org.springframework.test.web.reactive.server.WebTestClient

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CardDavControllerMinPrincipalTest {

    @Autowired
    lateinit var webClient: WebTestClient

    @Nested
    @DisplayName("Given PROPFIND request scenario")
    inner class GivenPropfindRequestScenario {

        private val defaultHeaders = HttpHeaders().apply {
            setBasicAuth("user", "password")
            set(HttpHeaders.CONTENT_TYPE, TEXT_XML_VALUE)
            set(HttpHeaders.ACCEPT, TEXT_XML_VALUE)
        }

        private val response = webClient
            .method(HttpMethod.valueOf("PROPFIND"))
            .uri("/codecentric/")
            .bodyValue(
                """
                <A:propfind xmlns:A="DAV:"> 
                    <A:prop> 
                        <B:addressbook-home-set xmlns:B="urn:ietf:params:xml:ns:carddav" /> 
                        <B:directory-gateway xmlns:B="urn:ietf:params:xml:ns:carddav" /> 
                        <A:displayname /> 
                        <C:email-address-set xmlns:C="http://calendarserver.org/ns/" /> 
                        <A:principal-collection-set /> 
                        <A:principal-URL /> 
                        <A:resource-id /> 
                        <A:supported-report-set /> 
                    </A:prop> 
                </A:propfind>
                """.trimIndent()
            )
            .headers { it.addAll(defaultHeaders) }
            .exchange()

        @Test
        @DisplayName("should respond with status 207 Multi-Status")
        fun shouldRespondWithStatus207MultiStatus() {
            response.expectStatus().isEqualTo(MULTI_STATUS)
        }

        @Test
        @DisplayName("should respond with content-type text/xml")
        fun shouldRespondWithContentTypeTextXml() {
            response.expectHeader().contentType(TEXT_XML_VALUE)
        }

        @Test
        @DisplayName("should have default CardDav headers")
        fun shouldHaveDefaultCardDavHeaders() {
            response.expectHeader().valueEquals("DAV", "1, 2, 3, calendar-access, addressbook, extended-mkcol")
        }

        @Test
        @DisplayName("should have response body")
        fun shouldHaveResponseBody() {
            response.expectBody().xml(
                """
                <multistatus xmlns="DAV:" xmlns:CR="urn:ietf:params:xml:ns:carddav" xmlns:CS="http://calendarserver.org/ns/">
                    <response>
                        <href>/codecentric/</href>
                        <propstat>
                            <prop>
                                <CR:addressbook-home-set>
                                    <href>/codecentric/</href>
                                </CR:addressbook-home-set>
                                <principal-collection-set>
                                    <href>/</href>
                                </principal-collection-set>
                                <principal-URL>
                                    <href>/codecentric/</href>
                                </principal-URL>
                                <supported-report-set>
                                    <supported-report>
                                        <report>
                                            <expand-property />
                                        </report>
                                    </supported-report>
                                    <supported-report>
                                        <report>
                                            <principal-search-property-set />
                                        </report>
                                    </supported-report>
                                    <supported-report>
                                        <report>
                                            <principal-property-search />
                                        </report>
                                    </supported-report>
                                </supported-report-set>
                            </prop>
                            <status>HTTP/1.1 200 OK</status>
                        </propstat>
                        <propstat>
                            <prop>
                                <CR:directory-gateway />
                                <displayname />
                                <CS:email-address-set />
                                <resource-id />
                            </prop>
                            <status>HTTP/1.1 404 Not Found</status>
                        </propstat>
                    </response>
                </multistatus>
                """.trimIndent()
            )
        }
    }
}