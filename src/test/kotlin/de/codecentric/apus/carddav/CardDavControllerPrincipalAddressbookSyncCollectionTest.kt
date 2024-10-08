package de.codecentric.apus.carddav

import de.codecentric.apus.vcard.VCardConfiguration
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus.MULTI_STATUS
import org.springframework.http.MediaType.TEXT_XML_VALUE
import org.springframework.test.web.reactive.server.WebTestClient

@AutoConfigureWebTestClient
@Import(VCardConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CardDavControllerPrincipalAddressbookSyncCollectionTest {

    @Autowired
    lateinit var webClient: WebTestClient

    @Nested
    @DisplayName("Given up to date REPORT request scenario")
    inner class UpToDateScenario {

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
                	<A:sync-token>2024-09-30T12:00</A:sync-token> 
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
        @DisplayName("should respond with content-type text/xml and charset utf-8")
        fun shouldRespondWithContentTypeTextXml() {
            response.expectHeader().contentType("text/xml; charset=utf-8")
        }

        @Test
        @DisplayName("should have default CardDav headers")
        fun shouldHaveDefaultCardDavHeaders() {
            response.expectHeader().valueEquals("DAV", "1, 2, 3, addressbook")
        }

        @Test
        @DisplayName("should have response body")
        fun shouldHaveResponseBody() {
            // TODO: Also test with outdated sync token
            response.expectBody().xml(
                """
                <multistatus xmlns="DAV:">
                	<sync-token>2024-09-30T12:00</sync-token>
                </multistatus>
                """.trimIndent()
            )
        }
    }

    @Nested
    @DisplayName("Given outdated REPORT request scenario")
    inner class OutdatedScenario {

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
                	<A:sync-token>2024-01-01T12:00</A:sync-token> 
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
        @DisplayName("should respond with content-type text/xml and charset utf-8")
        fun shouldRespondWithContentTypeTextXml() {
            response.expectHeader().contentType("text/xml; charset=utf-8")
        }

        @Test
        @DisplayName("should have default CardDav headers")
        fun shouldHaveDefaultCardDavHeaders() {
            response.expectHeader().valueEquals("DAV", "1, 2, 3, addressbook")
        }

        @Test
        @DisplayName("should have response body")
        fun shouldHaveResponseBody() {
            // TODO: Also test with outdated sync token
            response.expectBody().xml(
                """
                <multistatus xmlns="DAV:">
                   <response>
                      <href>/codecentric/addressbook/red.vcf</href>
                      <propstat>
                         <prop>
                            <getetag>2024-09-30T12:00</getetag>
                         </prop>
                         <status>HTTP/1.1 200 OK</status>
                      </propstat>
                   </response>
                   <response>
                      <href>/codecentric/addressbook/blue.vcf</href>
                      <propstat>
                         <prop>
                            <getetag>2024-09-30T12:00</getetag>
                         </prop>
                         <status>HTTP/1.1 200 OK</status>
                      </propstat>
                   </response>
                   <sync-token>2024-09-30T12:00</sync-token>
                </multistatus>
                """.trimIndent()
            )
        }
    }
}