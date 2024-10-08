package de.codecentric.apus.carddav

import com.ninjasquad.springmockk.MockkBean
import de.codecentric.apus.vcard.VCardConfiguration
import de.codecentric.apus.vcard.VCardWriter
import io.mockk.every
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
class CardDavControllerPrincipalAddressbookMultigetTest {

    @Autowired
    lateinit var webClient: WebTestClient

    @MockkBean(relaxed = true)
    private lateinit var writer: VCardWriter

    @Nested
    @DisplayName("Given PROPFIND request scenario")
    inner class GivenPropfindRequestScenario {

        private val defaultHeaders = HttpHeaders().apply {
            setBasicAuth("user", "password")
            set(HttpHeaders.CONTENT_TYPE, TEXT_XML_VALUE)
            set(HttpHeaders.ACCEPT, TEXT_XML_VALUE)
        }

        private val response: WebTestClient.ResponseSpec

        init {
            // Due to comparison issues we mock the writer response
            every { writer.write(any()) } returns arrayOf("VCARD_DATA")

            webClient
                .method(HttpMethod.valueOf("REPORT"))
                .uri("/codecentric/addressbook/")
                .bodyValue(
                    """
                    <B:addressbook-multiget xmlns:B="urn:ietf:params:xml:ns:carddav"> 
                        <A:prop xmlns:A="DAV:"> 
                            <A:getetag /> 
                            <B:address-data /> 
                        </A:prop> 
                        <A:href xmlns:A="DAV:">/codecentric/addressbook/red.vcf</A:href> 
                        <A:href xmlns:A="DAV:">/codecentric/addressbook/blue.vcf</A:href> 
                    </B:addressbook-multiget>
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
                <multistatus xmlns="DAV:" xmlns:CR="urn:ietf:params:xml:ns:carddav">
                	<response>
                		<href>/codecentric/addressbook/red.vcf</href>
                		<propstat>
                			<prop>
                				<getetag>2024-09-30T12:00</getetag>
                				<CR:address-data>
                                    VCARD_DATA
                                </CR:address-data>
                			</prop>
                			<status>HTTP/1.1 200 OK</status>
                		</propstat>
                	</response>
                	<response>
                		<href>/codecentric/addressbook/blue.vcf</href>
                		<propstat>
                			<prop>
                				<getetag>2024-09-30T12:00</getetag>
                				<CR:address-data>
                                    VCARD_DATA
                                </CR:address-data>
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