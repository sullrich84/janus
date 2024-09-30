package de.codecentric.janus.carddav

import com.ninjasquad.springmockk.MockkBean
import de.codecentric.janus.carddav.vcard.VCardService
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
class CardDavControllerPrincipalAddressbookDepth0Test {

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
            every { vCardService.getLatestCTag() } returns "SAMPLE_CTAG"
            every { vCardService.getLatestSyncToken() } returns "SAMPLE_SYNC_TOKEN"

            webClient
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
                <multistatus xmlns="DAV:" xmlns:CS="http://calendarserver.org/ns/">
                	<response>
                		<href>/codecentric/addressbook/</href>
                		<propstat>
                			<prop>
                				<CS:getctag>SAMPLE_CTAG</CS:getctag>
                				<sync-token>SAMPLE_SYNC_TOKEN</sync-token>
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