package de.codecentric.janus.carddav

import de.codecentric.janus.Namespace.DAV
import de.codecentric.janus.carddav.request.PropFindRequest
import io.kotest.matchers.maps.shouldHaveSize
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(classes = [CardDavService::class])
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
            val request = PropFindRequest(props, namespaces)

            val multiStatusResponse = subject.resolve("/", request)

            multiStatusResponse.ok shouldHaveSize 1
//            multiStatusResponse.ok.first()::class shouldBeSameInstanceAs CurrentUserPrincipal::class

            multiStatusResponse.notFound shouldHaveSize 1
//            multiStatusResponse.notFound.first()::class shouldBeSameInstanceAs Prop::class
        }
    }
}