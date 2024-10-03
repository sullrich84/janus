@file:Suppress("SpellCheckingInspection")

package de.codecentric.apus.vcard

import de.codecentric.apus.vcard.VCard.Companion.vcard
import io.kotest.assertions.withClue
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atTime
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import kotlin.test.Test

class VCardTest {

    private val vCard = vcard {
        meta {
            version = "4.0"
            rev = LocalDate(2024, 9, 30).atTime(LocalTime(12, 0))
            uid = "john-doe-12345"
        }

        bio {
            title = "Consultant Developer"
            nickname = "Johnny"
            givenName = "John"
            familyName = "Doe"
            honorific = "Dr."
            birthday = LocalDate(1980, 1, 1)
        }

        photo {
            type = VCard.ImageType.PNG
            // <editor-fold desc="bytes = {Base64 encoded image}">
            data = "iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAIAAABMXPacAAAEDmlDQ1BrQ0dDb2xvclNwYWNlR2VuZXJpY1JHQgAAOI2NVV1" +
                    "oHFUUPpu5syskzoPUpqaSDv41lLRsUtGE2uj+ZbNt3CyTbLRBkMns3Z1pJjPj/KRpKT4UQRDBqOCT4P9bwSchaqvtiy2itF" +
                    "CiBIMo+ND6R6HSFwnruTOzu5O4a73L3PnmnO9+595z7t4LkLgsW5beJQIsGq4t5dPis8fmxMQ6dMF90A190C0rjpUqlSYBG" +
                    "+PCv9rt7yDG3tf2t/f/Z+uuUEcBiN2F2Kw4yiLiZQD+FcWyXYAEQfvICddi+AnEO2ycIOISw7UAVxieD/Cyz5mRMohfRSwo" +
                    "qoz+xNuIB+cj9loEB3Pw2448NaitKSLLRck2q5pOI9O9g/t/tkXda8Tbg0+PszB9FN8DuPaXKnKW4YcQn1Xk3HSIry5ps8U" +
                    "Q/2W5aQnxIwBdu7yFcgrxPsRjVXu8HOh0qao30cArp9SZZxDfg3h1wTzKxu5E/LUxX5wKdX5SnAzmDx4A4OIqLbB69yMesE" +
                    "1pKojLjVdoNsfyiPi45hZmAn3uLWdpOtfQOaVmikEs7ovj8hFWpz7EV6mel0L9Xy23FMYlPYZenAx0yDB1/PX6dledmQjik" +
                    "jkXCxqMJS9WtfFCyH9XtSekEF+2dH+P4tzITduTygGfv58a5VCTH5PtXD7EFZiNyUDBhHnsFTBgE0SQIA9pfFtgo6cKGuho" +
                    "oeilaKH41eDs38Ip+f4At1Rq/sjr6NEwQqb/I/DQqsLvaFUjvAx+eWirddAJZnAj1DFJL0mSg/gcIpPkMBkhoyCSJ8lTZIx" +
                    "k0TpKDjXHliJzZPO50dR5ASNSnzeLvIvod0HG/mdkmOC0z8VKnzcQ2M/Yz2vKldduXjp9bleLu0ZWn7vWc+l0JGcaai10yN" +
                    "rUnXLP/8Jf59ewX+c3Wgz+B34Df+vbVrc16zTMVgp9um9bxEfzPU5kPqUtVWxhs6OiWTVW+gIfywB9uXi7CGcGW/zk98k/k" +
                    "mvJ95IfJn/j3uQ+4c5zn3Kfcd+AyF3gLnJfcl9xH3OfR2rUee80a+6vo7EK5mmXUdyfQlrYLTwoZIU9wsPCZEtP6BWGhAlh" +
                    "L3p2N6sTjRdduwbHsG9kq32sgBepc+xurLPW4T9URpYGJ3ym4+8zA05u44QjST8ZIoVtu3qE7fWmdn5LPdqvgcZz8Ww8BWJ" +
                    "8X3w0PhQ/wnCDGd+LvlHs8dRy6bLLDuKMaZ20tZrqisPJ5ONiCq8yKhYM5cCgKOu66Lsc0aYOtZdo5QCwezI4wm9J/v0X23" +
                    "mlZXOfBjj8Jzv3WrY5D+CsA9D7aMs2gGfjve8ArD6mePZSeCfEYt8CONWDw8FXTxrPqx/r9Vt4biXeANh8vV7/+/16ffMD1" +
                    "N8AuKD/A/8leAvFY9bLAAAAeGVYSWZNTQAqAAAACAAFARIAAwAAAAEAAQAAARoABQAAAAEAAABKARsABQAAAAEAAABSASgA" +
                    "AwAAAAEAAgAAh2kABAAAAAEAAABaAAAAAAAAAAoAAAABAAAACgAAAAEAAqACAAQAAAABAAAAgKADAAQAAAABAAAAgAAAAAA" +
                    "feNCXAAAACXBIWXMAAAGKAAABigEzlzBYAAABWWlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPHg6eG1wbWV0YSB4bWxucz" +
                    "p4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iWE1QIENvcmUgNi4wLjAiPgogICA8cmRmOlJERiB4bWxuczpyZGY9Imh0d" +
                    "HA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPgogICAgICA8cmRmOkRlc2NyaXB0aW9uIHJkZjph" +
                    "Ym91dD0iIgogICAgICAgICAgICB4bWxuczp0aWZmPSJodHRwOi8vbnMuYWRvYmUuY29tL3RpZmYvMS4wLyI+CiAgICAgICA" +
                    "gIDx0aWZmOk9yaWVudGF0aW9uPjE8L3RpZmY6T3JpZW50YXRpb24+CiAgICAgIDwvcmRmOkRlc2NyaXB0aW9uPgogICA8L3" +
                    "JkZjpSREY+CjwveDp4bXBtZXRhPgoZXuEHAAAHSklEQVR42u2dzW8kRxnGn6eqp3tm7P3wso5tEsJupKAcEApiNyiQE5dIc" +
                    "MoJ/gPuXDghTnDIkSP8DTmGAxLixgHlEAggPsJXliXZ3STrr/XMdHfVw6Ft7/hzx1Nl4ZD3kbxyz85UV9Xvfd+qnn79Nn+C" +
                    "NSRLoEe7tbL0YO2qDxFMaYwg6kej7//4h2tPPx3q2pF7Zzlzw0GxqPrvvfev737n20NcESIgQPP1zIMPEL6HK7dQjSCHDMr" +
                    "SiMkAGACTATAAJgNgAEwGwACYDIABMBkAA2AyAAbAZAAMgMkAGACTATAApk8cAH1C2pxfBdNySPZEgiTcbuYI558dggBJSJ" +
                    "CgiJQeRiFGRAFAj2iYbHNOe5ktcX/wJ3DmCa9M/1fRIhyayoM2wuNM5vCLAoXQxDiRvKQEQ+t6NglRZYmqDzo47p6DJ9v0C" +
                    "WNljKj6qEoAaEZAnegBAnpYrMCQKXoU17CQyQPCoOoPy54LUZzfZrtPNoso/vou1tfRtCmtSRG9EvfucfWzHA6htPgj9R3v" +
                    "vt/8aVRPQDdPqthhkylWBosZ5t8T2zFWg7ZfMUQwNayFqize/AWck1INFqRi65571oE6wWFmb2/o/e/04M4/Pggo952Jc08" +
                    "/UISYvCgJoIjYSm0XuE81cM3gAUHCpQWUJaKSFikBjqgneLAuOgAdUc3rnQKqqAW4Nt3K9hbh9PDT/ZCzrcCcsckohJi6CE" +
                    "OQQ3zcCEklhCCAAtK7dT7bUCKrjmbR6n/du8wjtAsxuxAzADYFBsAAmAyAATAZAANgMgAGwGQADIDJABgAkwEwAKbzV5HhJ" +
                    "o/O407R0RNo6hBPOuXBPuXPxWJGAMzQGWbu1nETetIvp34ks3FoatDMBMDn6JUjz2n2z9295re4PFkRcXsnR2gg0ca6EEkq" +
                    "3asEgA50cGnpB11nHEEoPRqRIEJoGjwMKIEmA4Cv/+xHGcw0xqLfv/+Hd/72+uu9GzdVN+TjofJgWiRO+H36bQFw7/5HUYd" +
                    "WAJ7VbQgEcOCGS71+MgBP99F485tfe/X2yud32sblyAwqrj7zTAZHigGDhdEH7xfY7m2N9dEo0UEdQNzB2QP/oY/svX/BX/" +
                    "qcT0jJ2p0sV+jR5vVLV5aWV5eaCZhhD1mEySSPBzgf2kaACqeBh08CIBJb14AJ4NImjUALDDWVjzVvWhxFwBdN26Kp6yaTB" +
                    "9BluRQQnCPZRV2FbhmYZ6TdPNF1cWgM9JCUA+uAETDk2ePYMVYBQHIk6EgyC4ALt03hoc0Gk9u6cLuoc7gSFkz2VYQBMBkA" +
                    "A2AyAAbAZAAMgMkAGACTATAAJgNgAEwG4MKqyNPMk8tDHLpvc/QQM1TyMAAzqrv7rUPz+8TCD1M5EMzfI50A/2zDyg8gxgz" +
                    "NRCFqtxKMJ6q9Mldzj5MUoNQCS4/Ju71om3IHdb8RKL1rewBcWabHHwWPqnS+IMCNCXZ2ErMZCPYQHLxLrjDWufmI6SbMgt" +
                    "wm5D2Kwim4LGkpj+7ezeDgUb2qGm9shGdfaJevq20Sb4WLvP/2P4fYihhMVcebAwBL6GPo+Tr2Eqtvgd5hqXXY3Gw+/nDU1" +
                    "FkA8Lf4gmZbUHXwDQcT37zH5uYrL95/6QVfN3AJ5i8IcM79+Ze/2vz92x4LwDjBk1yNZhmXb/WvxeQYTkBSVSz0XKVMK0JB" +
                    "DDnz6U8+dAToPWOkpLR1hRAiCqmH1NThrpMeCI9jWZprSqgK1yujYpZ85Iy7oIx7BOZK7ufx/eSpr0wfaragkARAmWZfmRn" +
                    "kaE7HX2LoSVN5/CFB7W7OkiLQNGG7Ep7HnZirIQNg3wUZAJMBMAAmA2AATAbAAJgMgAEwGQADYDIABsBkAD4NKj4dw8xXs4" +
                    "AAkZ6txPMAoKPNz9OK9v9lxq6FDKlUAhEi+sp4R6xghijUPS3NOefoPQullRHpqmU5OeZ4VleXYed5tU8pB1JJPkiC8mRFt" +
                    "BjlMDAnjOvtnfHGpm/SHzIn0LlJXQEurcQM0RKVx8Pm3sN4xAx1XNrwUZ+ernMTgUU8P1z7TGxDnnI1a7dexCmhQwc7e+zb" +
                    "JJSed7aWv/HVG6++xHF6KSl5X/z0w3tv/eWPlxfLuD13/S354eX1nY3bL7/yrddeCzEy2fjLsnzr1795542fD59ajXWbAcD" +
                    "SU6unIp8hj1xAWWgy6N284b70RYzGSCkCtZvO6TevX3sTuNEvtrc1X/UnQZcHvb/v4Obq8srtr7QhJE5WjFoY9O//+84PsP" +
                    "4yFyb1TgYAoW3SAxCc1Las62I8TgTQzbP33rcBwNWoAeZOV9dAAlA0bbMzCvsAeMbe7OGPio1UNM2XgRX4hr0MALIEst0Ha" +
                    "tM55+RcCoDd3jjXPUa4JdqDm6wzqfusHOld+iLMSHoncgw0UJ0pMy7/RjRjIykb+P3oSU39pUC+Z3pevKepXmxdzL9+sq8i" +
                    "DIABMBkAA2AyAAbAZAAMgMkAGACTATAAJgNgAEwGwACYDIABMBmA/1/9F8/2zPFqqFN/AAAAAElFTkSuQmCC"
            // </editor-fold desc="Base64 encoded image">
        }

        organization {
            name = "codecentric"
            branch = "Software Development"
            division = "Consulting"
            anniversary = LocalDate(2000, 1, 1)
        }

        address {
            street = "Hochstraße 11"
            postalCode = "42697"
            city = "Solingen"
            state = "Nordrhein-Westfalen"
            country = "Deutschland"
        }

        contact {
            email = "john.doe@codecentric.de"
            cellPhone = "+49 212 1234567"
        }

        additionalInfo {
            githubUrl = "https://github.com/codecentric"
            gitlabUrl = "https://gitlab.com/codecentric"
            atlasUrl = "https://atlas.codecentric.de/johndoe"
        }
    }

    val subject = vCard.toVCF().trim().split("\n")

    val snapshot = this::class.java.getResource("/snapshot/vcard.vcf")
        ?.readText()!!
        .split("\n")

    @Nested
    @DisplayName("Given vCard and snapshot")
    inner class GivenVCardAndSnapshot {

        @Test
        fun `should have identical line size`() {
            subject.size shouldBe snapshot.size
        }

        @Test
        fun `should have each line identical`() {
            subject.indices.forEach { line ->
                withClue("Comparing line $line") {
                    subject[line] shouldBe snapshot[line]
                }
            }
        }
    }
}