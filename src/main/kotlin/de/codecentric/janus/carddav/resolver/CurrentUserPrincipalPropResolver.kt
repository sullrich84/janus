package de.codecentric.janus.carddav.resolver

import org.springframework.stereotype.Component

@Component
class CurrentUserPrincipalPropResolver : PropResolver<CurrentUserPrincipal> {

    override fun supports(propName: String): Boolean {
        return propName == CurrentUserPrincipal.NAME
    }

    override fun resolve(): CurrentUserPrincipal {
        return CurrentUserPrincipal(href = "/codecentric/")
    }
}