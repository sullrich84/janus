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
class CardDavControllerPrincipalAddressbookDepth0Test {

    @Autowired
    private lateinit var webClient: WebTestClient

    @Nested
    @DisplayName("Given PROPFIND request scenario")
    inner class GivenPropfindRequestScenario {

        private val defaultHeaders = HttpHeaders().apply {
            setBasicAuth("user", "password")
            set(HttpHeaders.CONTENT_TYPE, TEXT_XML_VALUE)
            set(HttpHeaders.ACCEPT, TEXT_XML_VALUE)
        }

        private var response = webClient
            .method(HttpMethod.valueOf("PROPFIND"))
            .uri("/codecentric/addressbook/")
            .header("Depth", "0")
            .bodyValue(
                """
                <A:propfind xmlns:A="DAV:"> 
                    <A:prop> 
                        <C:getctag xmlns:C="http://calendarserver.org/ns/" /> 
                        <A:sync-token /> 
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
            response.expectBody().xml(
                """
                <multistatus xmlns="DAV:" xmlns:CS="http://calendarserver.org/ns/">
                	<response>
                		<href>/codecentric/addressbook/</href>
                		<propstat>
                			<prop>
                				<CS:getctag>2024-09-30T12:00</CS:getctag>
                				<sync-token>2024-09-30T12:00</sync-token>
                			</prop>
                			<status>HTTP/1.1 200 OK</status>
                		</propstat>
                	</response>
                </multistatus>
                """.trimIndent()
            )
        }
    }
}