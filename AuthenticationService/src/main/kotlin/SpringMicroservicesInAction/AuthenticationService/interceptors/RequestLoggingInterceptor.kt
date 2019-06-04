package SpringMicroservicesInAction.AuthenticationService.interceptors

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class RequestLoggingInterceptor : HandlerInterceptorAdapter() {

    val logger: Logger = LoggerFactory.getLogger(RequestLoggingInterceptor::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        val cachedRequest: HttpServletRequest = ContentCachingRequestWrapper(request)
        logger.debug("URI: ${cachedRequest.requestURI}" +
                "AUTH TYPE: ${cachedRequest.authType}" +
                "HEADERS: ${cachedRequest.headerNames}" +
                "PATH INFO: ${cachedRequest.pathInfo}")
        return super.preHandle(request, response, handler)
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        val cachedResponse: HttpServletResponse = ContentCachingResponseWrapper(response)
        logger.debug("STATUS: ${cachedResponse.status}")
        super.postHandle(request, response, handler, modelAndView)
    }
}