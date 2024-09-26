package de.codecentric.janus.carddav.resolver

import org.springframework.stereotype.Component

@Component
class PrincipalUrlPropResolver : PropResolver<PrincipalUrl> {

    override fun supports(propName: String): Boolean {
        return propName == PrincipalUrl.NAME
    }

    override fun resolve(): PrincipalUrl {
        // TODO: Use dynamic principal value
        return PrincipalUrl(href = "/codecentric/")
    }
}