@file:Suppress("SpellCheckingInspection")

package de.codecentric.apus.vcard

import org.springframework.stereotype.Component


/**
 * This class is responsible for converting {@link VCard} objects to the VCARD format.
 *
 * @author Sebastian Ullrich <sebastian.ullrich@codecentric.de>
 * @since 1.0.0
 */
@Component
class VCardWriter {

    fun write(vCard: VCard): Array<String> {
        return vCard.toVCF().split("\n").toTypedArray()
    }
}
