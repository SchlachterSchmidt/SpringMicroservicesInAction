package SpringMicroservicesInAction.GatewayService.filters

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TrackingFilter : ZuulFilter() {

    @Autowired
    lateinit var filterUtils: FilterUtils

    private val logger = LoggerFactory.getLogger(TrackingFilter::class.java)

    override fun shouldFilter(): Boolean = true

    override fun filterType(): String = FilterType.PRE.toString()

    override fun filterOrder(): Int = 1

    override fun run() {
        if (filterUtils.isCorrelationIdPresent()) {
            logger.debug("correlationId present in request: ${filterUtils.getCorrelationId()}")
        } else {
            filterUtils.generateAndSetCorrelationId()
            logger.debug("generated correlation id: ${filterUtils.getCorrelationId()}")
        }

        val context = RequestContext.getCurrentContext()
        logger.debug("processing request for ${context.request.requestURI}")
    }
}
