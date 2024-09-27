@file:Suppress("HttpUrlsUsage", "SpellCheckingInspection")

package de.codecentric.janus.carddav.request

import de.codecentric.janus.Namespace.*
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
    inner class GivenSingleNamespaceNotFoundBuilderFindRequest {

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
            subject.props shouldContainExactly mapOf(
                "current-user-principal" to DAV,
                "principal-URL" to DAV,
                "resourcetype" to DAV,
            )
        }

        @Test
        @DisplayName("should have expected name space uris and aliases")
        fun shouldHaveExpectedNameSpaceUrisAndAliases() {
            subject.namespaces shouldContainExactly mapOf(
                "A" to DAV
            )
        }
    }

    @Nested
    @DisplayName("Given multi namespace PropFind request")
    inner class GivenMultiNamespaceNotFoundBuilderFindRequest {

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
            subject.props shouldContainExactly mapOf(
                "addressbook-home-set" to CARDDAV,
                "directory-gateway" to CARDDAV,
                "displayname" to DAV,
                "email-address-set" to CALENDAR_SERVER,
                "principal-collection-set" to DAV,
                "principal-URL" to DAV,
                "resource-id" to DAV,
                "supported-report-set" to DAV,
            )
        }

        @Test
        @DisplayName("should have expected name space uris and aliases")
        fun shouldHaveExpectedNameSpaceUrisAndAliases() {
            subject.namespaces shouldContainExactly mapOf(
                "A" to DAV,
                "B" to CARDDAV,
                "C" to CALENDAR_SERVER,
            )
        }
    }

    private fun mockInputStream(payload: String): HttpInputMessage {
        val inputMessageMock = mockk<HttpInputMessage>(relaxed = true)
        every { inputMessageMock.body } returns toInputStream(payload, Charsets.UTF_8)
        return inputMessageMock
    }
}