@file:Suppress("HttpUrlsUsage", "SpellCheckingInspection")

package de.codecentric.janus.carddav.request

import io.kotest.matchers.maps.shouldContainExactly
import io.mockk.every
import io.mockk.mockk
import org.apache.commons.io.IOUtils.toInputStream
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.springframework.http.HttpInputMessage
import kotlin.test.Test


class PropFindRequestConverterTest {

    @Nested
    @DisplayName("Given single namespace PropFind request")
    inner class GivenSingleNameSpacePropFindRequest {

        private val inputMessageMock = mockInputStream(
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

        private val subject = PropFindRequestConverter().read(PropFindRequest::class.java, inputMessageMock)

        @Test
        @DisplayName("should have expected props and name space aliases")
        fun shouldHaveExpectedPropsAndNameSpaceAliases() {
            subject.prop shouldContainExactly mapOf(
                "current-user-principal" to "A",
                "principal-URL" to "A",
                "resourcetype" to "A",
            )
        }

        @Test
        @DisplayName("should have expected name space uris and aliases")
        fun shouldHaveExpectedNameSpaceUrisAndAliases() {
            subject.nsAliases shouldContainExactly mapOf(
                "A" to "DAV:"
            )
        }
    }

    @Nested
    @DisplayName("Given multi namespace PropFind request")
    inner class GivenMultiNameSpacePropFindRequest {

        private val inputMessageMock = mockInputStream(
            """
            <A:propfind xmlns:A="DAV:">
                <A:prop> 
                    <B:addressbook-home-set xmlns:B="urn:ietf:params:xml:ns:carddav" /> 
                    <B:directory-gateway xmlns:B="urn:ietf:params:xml:ns:carddav" /> 
                    <A:displayname /> 
                    <C:email-address-set xmlns:C="http://calendarserver.org/ns/" /> 
                    <A:principal-collection-set /> 
                    <A:principal-URL /> 
                    <A:resource-id /> 
                    <A:supported-report-set /> 
                </A:prop> 
           </A:propfind>  
           """.trimIndent()
        )

        private val subject = PropFindRequestConverter().read(PropFindRequest::class.java, inputMessageMock)

        @Test
        @DisplayName("should have expected props and name space aliases")
        fun shouldHaveExpectedPropsAndNameSpaceAliases() {
            subject.prop shouldContainExactly mapOf(
                "addressbook-home-set" to "B",
                "directory-gateway" to "B",
                "displayname" to "A",
                "email-address-set" to "C",
                "principal-collection-set" to "A",
                "principal-URL" to "A",
                "resource-id" to "A",
                "supported-report-set" to "A",
            )
        }

        @Test
        @DisplayName("should have expected name space uris and aliases")
        fun shouldHaveExpectedNameSpaceUrisAndAliases() {
            subject.nsAliases shouldContainExactly mapOf(
                "A" to "DAV:",
                "B" to "urn:ietf:params:xml:ns:carddav",
                "C" to "http://calendarserver.org/ns/",
            )
        }
    }

    private fun mockInputStream(payload: String): HttpInputMessage {
        val inputMessageMock = mockk<HttpInputMessage>(relaxed = true)
        every { inputMessageMock.body } returns toInputStream(payload, Charsets.UTF_8)
        return inputMessageMock
    }
}