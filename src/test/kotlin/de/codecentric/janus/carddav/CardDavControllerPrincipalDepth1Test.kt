package de.codecentric.janus.carddav

import com.ninjasquad.springmockk.MockkBean
import de.codecentric.janus.vcard.VCardService
import io.mockk.every
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
class CardDavControllerPrincipalDepth1Test {

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean
    private lateinit var vCardService: VCardService

    @Nested
    @DisplayName("Given PROPFIND request scenario")
    inner class GivenPropfindRequestScenario {

        private val defaultHeaders = HttpHeaders().apply {
            setBasicAuth("user", "password")
            set(HttpHeaders.CONTENT_TYPE, TEXT_XML_VALUE)
            set(HttpHeaders.ACCEPT, TEXT_XML_VALUE)
        }

        private var response: WebTestClient.ResponseSpec

        init {
            every { vCardService.getLatestSyncToken() } returns "SAMPLE_SYNC_TOKEN"

            webClient
                .method(HttpMethod.valueOf("PROPFIND"))
                .uri("/codecentric/")
                .header("Depth", "1")
                .bodyValue(
                    """
                <A:propfind xmlns:A="DAV:"> 
                    <A:prop> 
                        <A:add-member /> 
                        <D:bulk-requests xmlns:D="http://me.com/_namespace/" /> 
                        <A:current-user-privilege-set /> 
                        <A:displayname /> 
                        <D:guardian-restricted xmlns:D="http://me.com/_namespace/" /> 
                        <B:max-image-size xmlns:B="urn:ietf:params:xml:ns:carddav" /> 
                        <B:max-resource-size xmlns:B="urn:ietf:params:xml:ns:carddav" /> 
                        <C:me-card xmlns:C="http://calendarserver.org/ns/" /> 
                        <A:owner /> 
                        <C:push-transports xmlns:C="http://calendarserver.org/ns/" /> 
                        <C:pushkey xmlns:C="http://calendarserver.org/ns/" /> 
                        <A:quota-available-bytes /> 
                        <A:quota-used-bytes /> 
                        <A:resource-id /> 
                        <A:resourcetype /> 
                        <A:supported-report-set /> 
                        <A:sync-token /> 
                    </A:prop> 
                </A:propfind>
                """.trimIndent()
                )
                .headers { it.addAll(defaultHeaders) }
                .exchange()
                .also { res -> response = res }
        }

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
                <multistatus xmlns="DAV:" xmlns:CR="urn:ietf:params:xml:ns:carddav" xmlns:CS="http://calendarserver.org/ns/" xmlns:ME="http://me.com/_namespace/">
                    <response>
                        <href>/codecentric/</href>
                        <propstat>
                            <prop>
                                <current-user-privilege-set>
                                    <privilege>
                                        <read />
                                    </privilege>
                                    <privilege>
                                        <all />
                                    </privilege>
                                    <privilege>
                                        <write />
                                    </privilege>
                                    <privilege>
                                        <write-properties />
                                    </privilege>
                                    <privilege>
                                        <write-content />
                                    </privilege>
                                </current-user-privilege-set>
                                <owner>
                                    <href>/codecentric/</href>
                                </owner>
                                <resourcetype>
                                    <principal />
                                    <collection />
                                </resourcetype>
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
                                <add-member />
                                <ME:bulk-requests />
                                <displayname />
                                <ME:guardian-restricted />
                                <CR:max-image-size />
                                <CR:max-resource-size />
                                <CS:me-card />
                                <CS:push-transports />
                                <CS:pushkey />
                                <quota-available-bytes />
                                <quota-used-bytes />
                                <resource-id />
                                <sync-token />
                            </prop>
                            <status>HTTP/1.1 404 Not Found</status>
                        </propstat>
                    </response>
                    <response>
                        <href>/codecentric/addressbook/</href>
                        <propstat>
                            <prop>
                                <current-user-privilege-set>
                                    <privilege>
                                        <read />
                                    </privilege>
                                    <privilege>
                                        <all />
                                    </privilege>
                                    <privilege>
                                        <write />
                                    </privilege>
                                    <privilege>
                                        <write-properties />
                                    </privilege>
                                    <privilege>
                                        <write-content />
                                    </privilege>
                                </current-user-privilege-set>
                                <displayname>codecentric</displayname>
                                <owner>
                                    <href>/codecentric/</href>
                                </owner>
                                <resourcetype>
                                    <CR:addressbook />
                                    <collection />
                                </resourcetype>
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
                                    <supported-report>
                                        <report>
                                            <sync-collection />
                                        </report>
                                    </supported-report>
                                    <supported-report>
                                        <report>
                                            <CR:addressbook-multiget />
                                        </report>
                                    </supported-report>
                                    <supported-report>
                                        <report>
                                            <CR:addressbook-query />
                                        </report>
                                    </supported-report>
                                </supported-report-set>
                                <sync-token>SAMPLE_SYNC_TOKEN</sync-token>
                            </prop>
                            <status>HTTP/1.1 200 OK</status>
                        </propstat>
                        <propstat>
                            <prop>
                                <add-member />
                                <ME:bulk-requests />
                                <ME:guardian-restricted />
                                <CR:max-image-size />
                                <CR:max-resource-size />
                                <CS:me-card />
                                <CS:push-transports />
                                <CS:pushkey />
                                <quota-available-bytes />
                                <quota-used-bytes />
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