package de.codecentric.janus.carddav

import de.codecentric.janus.vcard.VCardConfiguration
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
class CardDavControllerPrincipalAddressbookTest {

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
            .uri("/codecentric/addressbook")
            .header("Depth", "0")
            .bodyValue(
                """
                <A:propfind xmlns:A="DAV:"> 
                	<A:prop> 
                		<A:current-user-principal /> 
                		<A:principal-URL /> 
                		<A:resourcetype /> 
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
                <multistatus xmlns="DAV:" xmlns:CR="urn:ietf:params:xml:ns:carddav">
                	<response>
                		<href>/codecentric/addressbook/</href>
                		<propstat>
                			<prop>
                				<current-user-principal>
                					<href>/codecentric/</href>
                				</current-user-principal>
                				<resourcetype>
                					<CR:addressbook />
                					<collection />
                				</resourcetype>
                			</prop>
                			<status>HTTP/1.1 200 OK</status>
                		</propstat>
                		<propstat>
                			<prop>
                				<principal-URL />
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