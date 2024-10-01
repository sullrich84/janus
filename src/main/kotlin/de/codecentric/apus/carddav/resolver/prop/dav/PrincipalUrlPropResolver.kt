package de.codecentric.apus.carddav.resolver.prop.dav

import de.codecentric.apus.carddav.request.RequestContext
import de.codecentric.apus.carddav.resolver.prop.ResolverContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml
import org.springframework.stereotype.Component

@Component
class PrincipalUrlPropResolver : DavPropResolver("principal-URL") {

    override fun supports(resolverContext: ResolverContext, requestContext: RequestContext): Boolean {
        return super.supports(resolverContext, requestContext)
                && resolverContext.href != "/"
                && resolverContext.href != "/${requestContext.principal}/addressbook/"
                && requestContext.principal != null
    }

    override fun resolve(resolverContext: ResolverContext, requestContext: RequestContext): Node {
        return xml(namespace.appendPrefix(propName)) {
            "href" {
                text("/${requestContext.principal}/")
            }
        }
    }
}