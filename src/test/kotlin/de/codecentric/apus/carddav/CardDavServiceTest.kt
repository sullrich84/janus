package de.codecentric.apus.carddav

import com.ninjasquad.springmockk.MockkBean
import de.codecentric.apus.carddav.Namespace.DAV
import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.request.RequestMethod
import de.codecentric.apus.carddav.request.WebDavRequest
import de.codecentric.apus.carddav.resolver.command.PropFindPrincipalResourcesResolver
import de.codecentric.apus.carddav.resolver.prop.dav.CurrentUserPrincipalPropResolver
import de.codecentric.apus.vcard.VCardService
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import java.net.URI

@SpringBootTest
@MockkBean(VCardService::class)
@ContextConfiguration(classes = [CardDavService::class, CurrentUserPrincipalPropResolver::class, PropFindPrincipalResourcesResolver::class])
class CardDavServiceTest {

    @Autowired
    lateinit var subject: CardDavService

    @Nested
    @DisplayName("Given prop resolver scenario")
    inner class GivenNotFoundBuilderResolverScenario {

        @Test
        @DisplayName("should find current-user-principal")
        fun shouldFindCurrentUserPrincipal() {
            val props = setOf(
                WebDavRequest.Prop("current-user-principal", DAV),
                WebDavRequest.Prop("some-unknown-prop", DAV),
            )

            val webDavRequest = WebDavRequest(
                method = RequestMethod.PROPFIND,
                namespace = DAV,
                props = props
            )

            val requestContext = RequestContext(
                depth = 0,
                brief = true,
                requestUri = URI("/anonymous/"),
                command = webDavRequest,
            )

            val multiStatusResponse = subject.resolve(requestContext).responses.first()

            multiStatusResponse.ok shouldHaveSize 1
            multiStatusResponse.ok.first().toString() shouldBe """
                <current-user-principal>
                	<href>
                		/anonymous/
                	</href>
                </current-user-principal>
            """.trimIndent()

            multiStatusResponse.notFound shouldHaveSize 1
            multiStatusResponse.notFound.first().toString() shouldBe "<some-unknown-prop/>"
        }
    }
}