package de.codecentric.apus.carddav.resolver.carddav

import com.ninjasquad.springmockk.MockkBean
import de.codecentric.apus.carddav.request.CardDavRequestContext
import de.codecentric.apus.carddav.resolver.ResolverContext
import de.codecentric.apus.vcard.VCardService
import de.codecentric.apus.vcard.VCardWriter
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(classes = [AddressDataPropResolver::class])
class AddressDataPropResolverTest {

    @Autowired
    private lateinit var subject: AddressDataPropResolver

    @MockkBean(relaxed = true)
    private lateinit var service: VCardService

    @MockkBean
    private lateinit var writer: VCardWriter

    @Test
    @DisplayName("should write multiline content")
    fun shouldWriteMultilineContent() {
        val resolverContext = mockk<ResolverContext>()
        val cardDavRequestContext = mockk<CardDavRequestContext>()

        every { resolverContext.href } returns "/codecentric/addressbook/red.vcf"
        every { writer.write(any()) } returns arrayOf("LINE_1","LINE_2")

        val node = subject.resolve(resolverContext, cardDavRequestContext)

        node.toString() shouldBe """
            <CR:address-data>
	LINE_1

	LINE_2

</CR:address-data>
        """.trimIndent()
    }
}