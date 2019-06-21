package SpringMicroservicesInAction.OrganizationService.hystrix

import com.netflix.hystrix.strategy.HystrixPlugins
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy
import org.springframework.beans.factory.annotation.Autowired
import javax.annotation.PostConstruct

class ThreadLocalConfig {

    @Autowired(required = false)
    lateinit var existingConcurrencyStrategy: HystrixConcurrencyStrategy

    @PostConstruct
    fun init() {
        val eventNotifier = HystrixPlugins.getInstance().eventNotifier
        val metricsPublisher = HystrixPlugins.getInstance().metricsPublisher
        val propertiesStrategy = HystrixPlugins.getInstance().propertiesStrategy
        val commandExecutionHook = HystrixPlugins.getInstance().commandExecutionHook

        HystrixPlugins.reset()

        HystrixPlugins.getInstance().registerConcurrencyStrategy(ThreadLocalAwareStrategy(existingConcurrencyStrategy))
        HystrixPlugins.getInstance().registerEventNotifier(eventNotifier)
        HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher)
        HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy)
        HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook)
    }
}