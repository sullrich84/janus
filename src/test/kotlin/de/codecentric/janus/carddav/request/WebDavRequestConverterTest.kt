@file:Suppress("HttpUrlsUsage", "SpellCheckingInspection")

package de.codecentric.janus.carddav.request

import de.codecentric.janus.Namespace.*
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.apache.commons.io.IOUtils.toInputStream
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.springframework.http.HttpInputMessage
import kotlin.test.Test


class WebDavRequestConverterTest {

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

        private val subject = WebDavRequestConverter().read(WebDavRequest::class.java, inputMessageMock)

        @Test
        @DisplayName("should have expected method")
        fun shouldHaveExpectedMethod() {
            subject.method shouldBe WebDavRequest.RequestMethod.PROPFIND
        }

        @Test
        @DisplayName("should have expected namespace")
        fun shouldHaveExpectedNamespace() {
            subject.namespace shouldBe DAV
        }

        @Test
        @DisplayName("should have expected props and name space aliases")
        fun shouldHaveExpectedPropsAndNameSpaceAliases() {
            subject.props shouldContainExactly listOf(
                WebDavRequest.Prop("current-user-principal", DAV),
                WebDavRequest.Prop("principal-URL", DAV),
                WebDavRequest.Prop("resourcetype", DAV),
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

        private val subject = WebDavRequestConverter().read(WebDavRequest::class.java, inputMessageMock)

        @Test
        @DisplayName("should have expected method")
        fun shouldHaveExpectedMethod() {
            subject.method shouldBe WebDavRequest.RequestMethod.PROPFIND
        }

        @Test
        @DisplayName("should have expected namespace")
        fun shouldHaveExpectedNamespace() {
            subject.namespace shouldBe DAV
        }

        @Test
        @DisplayName("should have expected props and name space aliases")
        fun shouldHaveExpectedPropsAndNameSpaceAliases() {
            subject.props shouldContainExactly listOf(
                WebDavRequest.Prop("addressbook-home-set", CARDDAV),
                WebDavRequest.Prop("directory-gateway", CARDDAV),
                WebDavRequest.Prop("displayname", DAV),
                WebDavRequest.Prop("email-address-set", CALENDAR_SERVER),
                WebDavRequest.Prop("principal-collection-set", DAV),
                WebDavRequest.Prop("principal-URL", DAV),
                WebDavRequest.Prop("resource-id", DAV),
                WebDavRequest.Prop("supported-report-set", DAV),
            )
        }
    }

    @Nested
    @DisplayName("Given SyncCollection request")
    inner class GivenSyncCollectionRequest {

        private val inputMessageMock = mockInputStream(
            """
            <A:sync-collection xmlns:A="DAV:"> 
                <A:sync-token>sample-sync-token</A:sync-token> 
                <A:sync-level>1</A:sync-level> 
                <A:prop> 
                    <A:getetag /> 
                </A:prop> 
            </A:sync-collection>
            """.trimIndent()
        )

        private val subject = WebDavRequestConverter().read(WebDavRequest::class.java, inputMessageMock)

        @Test
        @DisplayName("should have expected method")
        fun shouldHaveExpectedMethod() {
            subject.method shouldBe WebDavRequest.RequestMethod.SYNC_COLLECTION
        }

        @Test
        @DisplayName("should have expected namespace")
        fun shouldHaveExpectedNamespace() {
            subject.namespace shouldBe DAV
        }

        @Test
        @DisplayName("should have expected prop and name space")
        fun shouldHaveExpectedPropsAndNameSpaceAliases() {
            subject.props shouldContainExactly listOf(
                WebDavRequest.Prop("getetag", DAV),
            )
        }

        @Test
        @DisplayName("should have expected sync token")
        fun shouldHaveExpectedSyncToken() {
            subject.syncToken shouldBe "sample-sync-token"
        }

        @Test
        @DisplayName("should have expected sync level")
        fun shouldHaveExpectedSyncLevel() {
            subject.syncLevel shouldBe 1
        }
    }

    @Nested
    @DisplayName("Given AddressbookMultiget request")
    inner class GivenAddressbookMultigetRequest {

        private val inputMessageMock = mockInputStream(
            """
            <B:addressbook-multiget xmlns:B="urn:ietf:params:xml:ns:carddav"> 
                <A:prop xmlns:A="DAV:"> 
                    <A:getetag /> 
                    <B:address-data /> 
                </A:prop> 
                <A:href xmlns:A="DAV:">/codecentric/addressbook/RED.vcf</A:href> 
                <A:href xmlns:A="DAV:">/codecentric/addressbook/BLUE.vcf</A:href> 
            </B:addressbook-multiget>
            """.trimIndent()
        )

        private val subject = WebDavRequestConverter().read(WebDavRequest::class.java, inputMessageMock)

        @Test
        @DisplayName("should have expected method")
        fun shouldHaveExpectedMethod() {
            subject.method shouldBe WebDavRequest.RequestMethod.ADDRESSBOOK_MULTIGET
        }

        @Test
        @DisplayName("should have expected namespace")
        fun shouldHaveExpectedNamespace() {
            subject.namespace shouldBe CARDDAV
        }

        @Test
        @DisplayName("should have expected props and name space aliases")
        fun shouldHaveExpectedPropsAndNameSpaceAliases() {
            subject.props shouldContainExactly listOf(
                WebDavRequest.Prop("getetag", DAV),
                WebDavRequest.Prop("address-data", CARDDAV),
            )
        }

        @Test
        @DisplayName("should have expected hrefs")
        fun shouldHaveExpectedHrefs() {
            subject.hrefs shouldContainExactly listOf(
                "/codecentric/addressbook/RED.vcf",
                "/codecentric/addressbook/BLUE.vcf",
            )
        }
    }

    private fun mockInputStream(payload: String): HttpInputMessage {
        val inputMessageMock = mockk<HttpInputMessage>(relaxed = true)
        every { inputMessageMock.body } returns toInputStream(payload, Charsets.UTF_8)
        return inputMessageMock
    }
}