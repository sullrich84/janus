package de.codecentric.janus.carddav

import de.codecentric.janus.carddav.request.PropFindRequest
import de.codecentric.janus.carddav.resolver.CurrentUserPrincipal
import de.codecentric.janus.carddav.resolver.CurrentUserPrincipalPropResolver
import de.codecentric.janus.carddav.resolver.Prop
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.types.shouldBeSameInstanceAs
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
            val props = mapOf("current-user-principal" to "A", "some-unknown-prop" to "A")
            val nsAliases = mapOf("A" to "DAV:")
            val request = PropFindRequest(props, nsAliases)

            val multiStatusResponse = subject.resolve("/", request)

            multiStatusResponse.ok shouldHaveSize 1
            multiStatusResponse.ok.first()::class shouldBeSameInstanceAs CurrentUserPrincipal::class

            multiStatusResponse.notFound shouldHaveSize 1
            multiStatusResponse.notFound.first()::class shouldBeSameInstanceAs Prop::class
        }
    }
}