package de.codecentric.janus.carddav.request

import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class CardDavContextResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterType() == CardDavContext::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): CardDavContext {
        val depth = webRequest.getHeader("Depth").let { it?.toIntOrNull() ?: 0 }
        val briefPreferred = webRequest.getHeader("Brief").let { it?.lowercase() == "t" }
        val userAgent = webRequest.getHeader("User-Agent") ?: "unknown"

        return CardDavContext(depth = depth, briefPreferred = briefPreferred, userAgent = userAgent)
    }
}
