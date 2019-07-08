package SpringMicroservicesInAction.GatewayService.filters

import com.netflix.zuul.context.RequestContext
import org.springframework.stereotype.Component

@Component
class FilterUtils {
    private val CORRELATION_ID_HEADER = "tmx-correlation-id"
    private val ORG_ID_HEADER = "tmx-org-id"
    private val USER_ID_HEADER = "tmx-user-id"
    private val AUTH_TOKEN_HEADER = "tmx-auth-token"

    fun getCorrelationId(): String? = getValueFromRequestHeaderOrZuulRequestHeader(CORRELATION_ID_HEADER)

    private fun getValueFromRequestHeaderOrZuulRequestHeader(headerName: String): String? {
        val context = RequestContext.getCurrentContext()
        return context.request.getHeader(headerName) ?: context.zuulRequestHeaders[headerName]
    }

    fun generateAndSetCorrelationId() {
        val correlationId = generateCorrelationId()
        setZuulRequestHeader(CORRELATION_ID_HEADER, correlationId)
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

    fun getOrgId(): String? = getValueFromRequestHeaderOrZuulRequestHeader(ORG_ID_HEADER)

    fun setOrgId(orgId: String) = setZuulRequestHeader(ORG_ID_HEADER, orgId)

    fun getUserId(): String? = getValueFromRequestHeaderOrZuulRequestHeader(USER_ID_HEADER)

    fun setUserId(userId: String) = setZuulRequestHeader(USER_ID_HEADER, userId)

    fun getAuthToken(): String? = getValueFromRequestHeaderOrZuulRequestHeader(AUTH_TOKEN_HEADER)

    fun getServiceId(): String = RequestContext.getCurrentContext()["serviceId"].toString()
}

enum class FilterType {
    PRE, POST, ROUTE
}