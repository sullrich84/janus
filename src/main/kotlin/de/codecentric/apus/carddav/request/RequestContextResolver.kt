package de.codecentric.apus.carddav.request

import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.net.URI

@Component
class RequestContextResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == RequestContext::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        request: NativeWebRequest,
        binder: WebDataBinderFactory?,
    ): RequestContext {
        val nativeRequest = request.nativeRequest as HttpServletRequest
        val requestUri = URI(nativeRequest.requestURL.toString())

        val depth = request.getHeader("depth")?.toIntOrNull() ?: 0
        val brief = request.getHeader("brief") ?: "f"
        val agent = request.getHeader("user-agent") ?: "n/a"

        // TODO: Use Spring Security to get the principal
        val principal = request.getHeader("principal") ?: "anonymous"

        return RequestContext(
            requestUri = requestUri,
            principal = principal,
            depth = depth,
            brief = brief == "t",
            agent = agent,
        )
    }
}