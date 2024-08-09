package de.codecentric.janus.core

import io.micrometer.tracing.Tracer
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

/**
 * A filter that adds a request ID header to the HTTP response.
 * This filter is executed once per request and uses the Micrometer Tracer to obtain the current span's trace ID.
 *
 * @property tracer The Micrometer Tracer used to obtain the current span's trace ID.
 * @author Sebastian Ullrich <sebastian.ullrich@codecentric.de>
 * @since 1.0.0
 */
@Component
class SpanExportFilter(private val tracer: Tracer) : OncePerRequestFilter() {

    companion object {
        const val REQUEST_ID_HEADER = "X-Request-Id"
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filter: FilterChain) {
        response.setHeader(REQUEST_ID_HEADER, tracer.currentSpan()?.context()?.traceId())
    }
}
