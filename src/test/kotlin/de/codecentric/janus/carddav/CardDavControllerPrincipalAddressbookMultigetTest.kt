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
class CardDavControllerPrincipalAddressbookMultigetTest {

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
                <B:addressbook-multiget xmlns:B="urn:ietf:params:xml:ns:carddav"> 
                	<A:prop xmlns:A="DAV:"> 
                		<A:getetag /> 
                		<B:address-data /> 
                	</A:prop> 
                	<A:href xmlns:A="DAV:">/codecentric/addressbook/RED.vcf</A:href> 
                	<A:href xmlns:A="DAV:">/codecentric/addressbook/BLUE.vcf</A:href> 
                </B:addressbook-multiget>
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
                		<href>/codecentric/addressbook/RED.vcf</href>
                		<propstat>
                			<prop>
                				<getetag>RED_ETAG</getetag>
                				<CR:address-data>RED_DATA</CR:address-data>
                			</prop>
                			<status>HTTP/1.1 200 OK</status>
                		</propstat>
                	</response>
                	<response>
                		<href>/codecentric/addressbook/BLUE.vcf</href>
                		<propstat>
                			<prop>
                				<getetag>BLUE_ETAG</getetag>
                				<CR:address-data>BLUE_DATA</CR:address-data>
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