package de.codecentric.janus.carddav.resolver

import org.springframework.stereotype.Component

@Component
class ResourceTypePropResolver : PropResolver<ResourceType> {

    override fun supports(propName: String): Boolean {
        return propName == ResourceType.NAME
    }

    override fun resolve(): ResourceType {
        return ResourceType()
    }
}