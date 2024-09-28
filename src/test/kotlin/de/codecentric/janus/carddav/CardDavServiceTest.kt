package de.codecentric.janus.carddav

import de.codecentric.janus.Namespace.DAV
import de.codecentric.janus.carddav.request.CardDavRequestContext
import de.codecentric.janus.carddav.resolver.CurrentUserPrincipalPropResolver
import de.codecentric.janus.carddav.request.PropFindRequest
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(classes = [CardDavService::class, CurrentUserPrincipalPropResolver::class])
class CardDavServiceTest {

    @Autowired
    lateinit var subject: CardDavService

    @Nested
    @DisplayName("Given prop resolver scenario")
    inner class GivenNotFoundBuilderResolverScenario {

        @Test
        @DisplayName("should find current-user-principal")
        fun shouldFindCurrentUserPrincipal() {
            val props = mapOf("current-user-principal" to DAV, "some-unknown-prop" to DAV)
            val namespaces = mapOf("A" to DAV)
            val propFindRequest = PropFindRequest(props, namespaces)
            val cardDavRequestContext = CardDavRequestContext(
                depth = 0,
                brief = true,
                locations = listOf(element = "/"),
                propFindRequest = propFindRequest,
            )

            val multiStatusResponse = subject.resolve(cardDavRequestContext = cardDavRequestContext).responses.first()

            multiStatusResponse.ok shouldHaveSize  1
            multiStatusResponse.ok.first().toString() shouldBe """
                <current-user-principal>
                	<href>
                		/codecentric/
                	</href>
                </current-user-principal>
            """.trimIndent()

            multiStatusResponse.notFound shouldHaveSize 1
            multiStatusResponse.notFound.first().toString() shouldBe "<some-unknown-prop/>"
        }
    }
}