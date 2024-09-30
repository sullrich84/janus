package de.codecentric.janus.carddav

import org.junit.jupiter.api.Disabled
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

@Disabled
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CardDavControllerPrincipalAddressbookSyncCollectionTest {

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
            .method(HttpMethod.valueOf("REPORT"))
            .uri("/codecentric/addressbook/")
            .header("Depth", "1")
            .bodyValue(
                """
                <A:sync-collection xmlns:A="DAV:"> 
                	<A:sync-token>SYNC_TOKEN</A:sync-token> 
                	<A:sync-level>1</A:sync-level> 
                	<A:prop> 
                		<A:getetag /> 
                	</A:prop> 
                </A:sync-collection>
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
                // TODO: No etag props due to no changes
                """
                <multistatus xmlns="DAV:">
                	<sync-token>http://radicale.org/ns/sync/90a7f96c63d46e483b2d0a0b489d28e2460d1ccbd583f1f4aa7743a90ca9a6a5</sync-token>
                </multistatus>
                """.trimIndent()
            )
        }
    }
}