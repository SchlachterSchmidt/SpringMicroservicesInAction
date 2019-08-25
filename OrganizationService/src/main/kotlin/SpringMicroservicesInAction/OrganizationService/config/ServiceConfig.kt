package SpringMicroservicesInAction.OrganizationService.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ServiceConfig {

    @Value("\${application.timeout.enabled}")
    val randomTimeoutEnabled: String = ""

    fun randomTimeoutIsEnabled(): Boolean {
        return randomTimeoutEnabled.toBoolean()
    }
}