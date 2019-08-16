package SpringMicroservicesInAction.OrganizationService.events.source

import SpringMicroservicesInAction.OrganizationService.utils.UserContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.messaging.Source
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class SimpleSourceBean(@Autowired val source: Source) {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun publishOrgChange(action: String, orgId: String) {
        logger.debug("Sending change message to Kafka for $orgId with action: $action")
        val change = OrganizationChangeModel(
                action = action,
                organizationId = orgId,
                type = "OrganizationChangeModel",
                correlationId = UserContext.correlationId!!)
        source
            .output()
            .send(
                MessageBuilder
                    .withPayload(change)
                    .build())
    }
}