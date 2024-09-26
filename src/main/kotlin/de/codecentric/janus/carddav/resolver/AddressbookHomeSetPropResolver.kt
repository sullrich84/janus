package de.codecentric.janus.carddav.resolver

import org.springframework.stereotype.Component

@Component
class AddressbookHomeSetPropResolver : PropResolver<AddressbookHomeSet> {

    override fun supports(propName: String): Boolean {
        return propName == AddressbookHomeSet.NAME
    }

    override fun resolve(): AddressbookHomeSet {
        // TODO: use dynamic addressbook home set value
        return AddressbookHomeSet(href = "/codecentric/")
    }
}