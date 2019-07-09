package SpringMicroservicesInAction.GatewayService.filters

import com.netflix.zuul.context.RequestContext
import org.springframework.stereotype.Component

@Component
class FilterUtils {
    fun getCorrelationId(): String? = getValueFromRequestHeaderOrZuulRequestHeader(HeaderNames.CORRELATION_ID_HEADER.headerName)

    private fun getValueFromRequestHeaderOrZuulRequestHeader(headerName: String): String? {
        val context = RequestContext.getCurrentContext()
        return context.request.getHeader(headerName) ?: context.zuulRequestHeaders[headerName]
    }

    fun generateAndSetCorrelationId() {
        val correlationId = generateCorrelationId()
        setZuulRequestHeader(HeaderNames.CORRELATION_ID_HEADER.headerName, correlationId)
    }

    private fun generateCorrelationId(): String = java.util.UUID.randomUUID().toString()

    private fun setZuulRequestHeader(headerName: String, value: String) {
        val context = RequestContext.getCurrentContext()
        context.addZuulRequestHeader(headerName, value)
    }

    fun isCorrelationIdPresent(): Boolean {
        if (getCorrelationId() == null) return false
        return true
    }

    fun getOrgId(): String? = getValueFromRequestHeaderOrZuulRequestHeader(HeaderNames.ORG_ID_HEADER.headerName)

    fun setOrgId(orgId: String) = setZuulRequestHeader(HeaderNames.ORG_ID_HEADER.headerName, orgId)

    fun getUserId(): String? = getValueFromRequestHeaderOrZuulRequestHeader(HeaderNames.USER_ID_HEADER.headerName)

    fun setUserId(userId: String) = setZuulRequestHeader(HeaderNames.USER_ID_HEADER.headerName, userId)

    fun getAuthToken(): String? = getValueFromRequestHeaderOrZuulRequestHeader(HeaderNames.AUTH_TOKEN_HEADER.headerName)

    fun getServiceId(): String = RequestContext.getCurrentContext()["serviceId"].toString()
}

enum class FilterType(val type: String) {
    PRE("pre"), POST("post"), ROUTE("route")
}

enum class HeaderNames(val headerName: String) {
    CORRELATION_ID_HEADER("tmx-correlation-id"),
    ORG_ID_HEADER("tmx-org-id"),
    USER_ID_HEADER("tmx-user-id"),
    AUTH_TOKEN_HEADER("tmx-auth-token")
}