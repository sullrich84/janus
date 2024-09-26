package de.codecentric.janus.carddav.resolver

import org.springframework.stereotype.Component

@Component
class PrincipalCollectionSetPropResolver : PropResolver<PrincipalCollectionSet> {

    override fun supports(propName: String): Boolean {
        return propName == PrincipalCollectionSet.NAME
    }

    override fun resolve(): PrincipalCollectionSet {
        return PrincipalCollectionSet(href = "/")
    }
}