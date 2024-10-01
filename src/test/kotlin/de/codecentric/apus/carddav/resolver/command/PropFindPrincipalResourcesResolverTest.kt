package de.codecentric.apus.carddav.resolver.command

import de.codecentric.apus.carddav.request.RequestContext
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class PropFindPrincipalResourcesResolverTest {

    private val subject = PropFindPrincipalResourcesResolver()

    val context = mockk<RequestContext> {
        every { principal } returns "principal"
    }

    @Test
    fun `should resolve only principal for depth 0`() {
        every { context.depth } returns 0
        subject.resolveLocations(context) shouldContainExactly setOf("/principal/")
    }

    @Test
    fun `should resolve principal and addressbook for depth 1`() {
        every { context.depth } returns 1
        subject.resolveLocations(context) shouldContainExactly setOf("/principal/", "/principal/addressbook/")
    }
}