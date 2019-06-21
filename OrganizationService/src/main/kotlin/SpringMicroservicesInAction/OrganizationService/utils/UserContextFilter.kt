package SpringMicroservicesInAction.OrganizationService.utils

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


@Component
class UserContextFilter : Filter {

    val logger = LoggerFactory.getLogger(UserContextFilter::class.java)

    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, chain: FilterChain?) {
        val request = servletRequest as HttpServletRequest

        val userContext = UserContextHolder.getContext()
        userContext.correlationId = request.getHeader(UserContext.CORRELATION_ID)
        userContext.authToken = request.getHeader(UserContext.AUTH_TOKEN)
        userContext.orgId = request.getHeader(UserContext.ORG_ID)
        userContext.userId = request.getHeader(UserContext.USER_ID)

        logger.debug("UserContextFilter Correlation id: ${userContext.correlationId}")

        chain?.doFilter(request, servletResponse)
    }
}