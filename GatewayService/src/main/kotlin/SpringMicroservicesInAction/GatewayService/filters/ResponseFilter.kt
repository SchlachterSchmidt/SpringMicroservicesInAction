package SpringMicroservicesInAction.GatewayService.filters

import brave.Tracer
import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ResponseFilter : ZuulFilter() {

    @Autowired
    lateinit var filterUtils: FilterUtils

    @Autowired
    lateinit var tracer: Tracer

    val logger: Logger = LoggerFactory.getLogger(ResponseFilter::class.java)

    override fun shouldFilter(): Boolean = true

    override fun filterType(): String = FilterType.POST.type

    override fun filterOrder(): Int = 1

    override fun run() {
        val context = RequestContext.getCurrentContext()

        logger.debug("Adding the correlation id to the outbound headers: ${filterUtils.getCorrelationId()}")
        context.response.addHeader(HeaderNames.CORRELATION_ID_HEADER.headerName, filterUtils.getCorrelationId())
        context.response.addHeader(HeaderNames.SLEUTH_TRACE_HEADER.headerName, tracer.currentSpan().context().traceIdString())

        logger.debug("Completing outgoing request for  ${context.request.requestURI}")
    }
}